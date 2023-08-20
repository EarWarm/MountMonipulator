package ru.mountcode.programms.mountmanipulator.transformers.settings;

import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;
import ru.mountcode.yaml.configuration.ConfigurationSection;

import java.lang.reflect.Field;

public interface ISettingTransformer extends ITransformer {

    default void loadSettings(ConfigurationSection section) {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(SettingValue.class)) {
                SettingValue value = field.getAnnotation(SettingValue.class);
                if (section.contains(value.section())) {
                    field.setAccessible(true);
                    try {
                        field.set(this, section.get(value.section()));
                    } catch (IllegalAccessException e) {
                        MountManipulator.LOGGER.error("Error on loading transformer setting value", e);
                    }
                }
            }
        }
    }

    default ConfigurationSection saveSettings(ConfigurationSection section) {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(SettingValue.class)) {
                SettingValue value = field.getAnnotation(SettingValue.class);
                field.setAccessible(true);
                try {
                    section.set(value.section(), field.get(this));
                } catch (IllegalAccessException e) {
                    MountManipulator.LOGGER.error("Error on saving transformer setting value", e);
                }
            }
        }
        return section;
    }
}
