package ru.mountcode.programms.mountmanipulator.lang;

import java.util.Locale;
import java.util.ResourceBundle;

public class TranslateMappings {

    private final ResourceBundle resourceBundle;

    public TranslateMappings(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle("locales.locale", locale);
    }

    public String getString(String key) {
        return this.resourceBundle.getString(key);
    }

    public Object getObject(String key) {
        return this.resourceBundle.getObject(key);
    }

    public String[] getStringArray(String key) {
        return this.resourceBundle.getStringArray(key);
    }
}
