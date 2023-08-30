package ru.mountcode.programms.mountmanipulator.ui.panes.workspace;

import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.events.WorkspaceEvents;
import ru.mountcode.programms.mountmanipulator.lang.Lang;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.ClassesTreeView;

import java.io.File;

public class ClassesPane extends BorderPane {

    private final ClassesTreeView classesTreeView;
    private final Label placeholder = new Label(Lang.translatable("input.drop"));

    public ClassesPane() {
        this.classesTreeView = new ClassesTreeView();
        this.setCenter(this.placeholder);

        if (MountManipulator.getInstance().getWorkspace().getJarObject() != null) {
            this.classesTreeView.fillTree(MountManipulator.getInstance().getWorkspace().getJarObject().classCollection());
            this.classesTreeView.refresh();
            this.setCenter(this.classesTreeView);
        }

        WorkspaceEvents.RESET.register(() -> {
            this.classesTreeView.setRoot(null);
            this.classesTreeView.refresh();
            this.setCenter(this.placeholder);
        });

        WorkspaceEvents.LOAD_INPUT.register(jarObject -> {
            this.classesTreeView.fillTree(jarObject.classCollection());
            this.classesTreeView.refresh();
            this.setCenter(this.classesTreeView);
        });

        WorkspaceEvents.STOP_EXECUTING.register(() -> {
            this.classesTreeView.fillTree(MountManipulator.getInstance().getWorkspace().getJarObject().classCollection());
            this.classesTreeView.refresh();
        });

        this.setOnDragOver(this::onDragOver);
        this.setOnDragDropped(this::onDragDrop);
    }

    private void onDragOver(DragEvent e) {
        if (e.getGestureSource() != this && e.getDragboard().hasFiles())
            e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        e.consume();
    }

    private void onDragDrop(DragEvent e) {
        if(e.getDragboard().hasFiles()) {
            File file = e.getDragboard().getFiles().get(0);
            MountManipulator.getInstance().getWorkspace().loadInputJar(file);
        }
    }

    public Label getPlaceholder() {
        return placeholder;
    }

    public ClassesTreeView getClassesTreeView() {
        return classesTreeView;
    }
}
