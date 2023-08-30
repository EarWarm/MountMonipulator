package ru.mountcode.programms.mountmanipulator.ui.controls.editor;

import javafx.concurrent.Task;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import ru.mountcode.programms.mountmanipulator.ui.language.Language;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyntaxArea extends CodeArea {

    private final Language language;

    private final ExecutorService executorService;

    public SyntaxArea(Language language) {
        this.language = language;
        this.getStyleClass().add("syntax-area");
        this.getStyleClass().add("analyser");
        this.getStylesheets().add(this.language.getStyleFile());

        this.executorService = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            thread.setDaemon(true);
            return thread;
        });

        this.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(this::createStyleSpansAsync)
                .awaitLatest(this.richChanges())
                .filterMap(objectTry -> {
                    if (objectTry.isSuccess()) {
                        return Optional.of(objectTry.get());
                    } else {
                        // TODO: Log not implemented
                        objectTry.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyStyleSpans);
    }

    public void setText(String text) {
        if (text == null) {
            this.clear();

        } else {
            replaceText(text);
        }
        this.getUndoManager().forgetHistory();
        this.getUndoManager().mark();
    }

    private Task<StyleSpans<Collection<String>>> createStyleSpansAsync() {
        String text = this.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<>() {
            @Override
            protected StyleSpans<Collection<String>> call() {
                return language.createStyleSpans(text);
            }
        };
        executorService.execute(task);
        return task;
    }

    private void applyStyleSpans(StyleSpans<Collection<String>> styleSpans) {
        this.setStyleSpans(0, styleSpans);
    }

    public Language getLanguage() {
        return language;
    }
}
