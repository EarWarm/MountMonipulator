package ru.mountcode.programms.mountmanipulator.lang;

import ru.mountcode.programms.mountmanipulator.MountManipulator;

import java.util.HashMap;
import java.util.Locale;

public class Lang {

    private static final HashMap<String, Locale> locales = new HashMap<>();

    private static Lang instance;

    static {
        locales.put("ru", Locale.of("ru"));
        locales.put("en_US", Locale.of("en", "US"));
    }

    private TranslateMappings translateMappings;


    private Lang() {
        instance = this;
        Locale locale = locales.get(MountManipulator.getInstance().getConfiguration().getLocale());
        Locale.setDefault(locale);

        this.translateMappings = new TranslateMappings(locale);
    }

    public static void updateLocale(String locale) {
        getInstance().setTranslateMappings(new TranslateMappings(locales.get(locale)));
    }

    public static String translatable(String key) {
        return getInstance().translateMappings.getString(key);
    }

    public static HashMap<String, Locale> getLocales() {
        return locales;
    }

    private void setTranslateMappings(TranslateMappings translateMappings) {
        this.translateMappings = translateMappings;
    }

    public static Lang getInstance() {
        return instance != null ? instance : new Lang();
    }
}
