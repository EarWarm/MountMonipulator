package ru.mountcode.programms.mountmanipulator.ui.language;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import ru.mountcode.yaml.configuration.ConfigurationSection;
import ru.mountcode.yaml.configuration.file.YamlConfiguration;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;

public class Language {

    private static String defaultStylesDirectory = "/assets/styles/languages/";
    private static String defaultAnalyserDirectory = "/languages/";

    private final String name;

    private LanguageAnalyser analyser;

    private final String styleFile;

    public Language(String name) {
        this.name = name;

        this.styleFile = defaultStylesDirectory + this.name.toLowerCase() + ".css";
        this.loadAnalyser(defaultAnalyserDirectory + this.name.toLowerCase() + ".yml");
    }

    public Language(String name, String styleFile, String analyserConfigFile) {
        this.name = name;
        this.styleFile = styleFile;

        this.loadAnalyser(analyserConfigFile);
    }

    public StyleSpans<Collection<String>> createStyleSpans(String text) {
        Matcher matcher = this.analyser.createMatcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass = this.analyser.getStyleClass(matcher);
            spansBuilder.add(Collections.singleton("plain"), matcher.start() - lastKwEnd);
            if (styleClass == null) {
                continue;
            }
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.singleton("plain"), text.length() - lastKwEnd);

        return spansBuilder.create();
    }

    public static void setDefaultStylesDirectory(String defaultStylesDirectory) {
        Language.defaultStylesDirectory = defaultStylesDirectory;
    }

    public static void setDefaultAnalyserDirectory(String defaultAnalyserDirectory) {
        Language.defaultAnalyserDirectory = defaultAnalyserDirectory;
    }

    private void loadAnalyser(String analyserUrl) {
        try {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File(Language.class.getResource(analyserUrl).toURI()));

            ConfigurationSection patternsSection = configuration.getConfigurationSection("patterns");
            HashMap<String, String> patterns = new HashMap<>();
            for (String key : patternsSection.getKeys(false)) {
                patterns.put(key, patternsSection.getString(key));
            }

            this.analyser = new LanguageAnalyser(patterns);
        } catch (URISyntaxException e) {
            // TODO: Log not implemented
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    public LanguageAnalyser getAnalyser() {
        return analyser;
    }

    public String getStyleFile() {
        return styleFile;
    }
}
