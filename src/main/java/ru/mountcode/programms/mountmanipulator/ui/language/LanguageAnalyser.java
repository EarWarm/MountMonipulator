package ru.mountcode.programms.mountmanipulator.ui.language;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageAnalyser {

    private final HashMap<String, String> patterns;
    private Pattern pattern;

    public LanguageAnalyser(HashMap<String, String> patterns) {
        this.patterns = patterns;
        this.compilePattern();
    }

    private void compilePattern() {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : patterns.entrySet()) {
            builder.append("(?<").append(entry.getKey()).append(">").append(entry.getValue()).append(")|");
        }
        builder.deleteCharAt(builder.length() - 1);
        this.pattern = Pattern.compile(builder.toString());
    }


    public Matcher createMatcher(String text) {
        return this.pattern.matcher(text);
    }

    public String getStyleClass(Matcher matcher) {
        for (String pattern : patterns.keySet()) {
            if (matcher.group(pattern) != null) {
                return pattern;
            }
        }

        return null;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public HashMap<String, String> getPatterns() {
        return patterns;
    }
}
