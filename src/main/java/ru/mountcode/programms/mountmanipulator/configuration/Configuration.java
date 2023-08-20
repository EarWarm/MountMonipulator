package ru.mountcode.programms.mountmanipulator.configuration;

import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;
import ru.mountcode.programms.mountmanipulator.transformers.Transformers;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;
import ru.mountcode.programms.mountmanipulator.transformers.settings.ISettingTransformer;
import ru.mountcode.yaml.configuration.ConfigurationSection;
import ru.mountcode.yaml.configuration.InvalidConfigurationException;
import ru.mountcode.yaml.configuration.SmartConfiguration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Configuration extends SmartConfiguration {

    private final HashMap<String, TransformersGroup> transformersGroups = new HashMap<>();

    @Override
    public void initialize() throws IOException, InvalidConfigurationException {
        super.initialize();

        ConfigurationSection groupsSection = this.getConfigurationSection("groups");
        if (groupsSection != null) {
            for (String name : groupsSection.getKeys(false)) {
                TransformersGroup group = new TransformersGroup(name);
                ConfigurationSection groupSection = groupsSection.getConfigurationSection(name);
                if (groupSection == null) {
                    this.transformersGroups.put(name, group);
                    continue;
                }

                for (String transformerName : groupSection.getKeys(false)) {
                    ConfigurationSection transformerSection = groupSection.getConfigurationSection(transformerName);
                    if (transformerSection == null) {
                        continue;
                    }

                    if (!Transformers.getTransformers().containsKey(transformerName)) {
                        continue;
                    }

                    if (transformerSection.getKeys(false).size() > 0) {
                        try {
                            ITransformer transformer = Transformers.getTransformers().get(transformerName).getConstructor().newInstance();
                            if (!(transformer instanceof ISettingTransformer)) {
                                group.addTransformer(transformer);
                                continue;
                            }
                            ((ISettingTransformer) transformer).loadSettings(transformerSection);
                            group.addTransformer(transformer);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                 NoSuchMethodException e) {
                            MountManipulator.LOGGER.error("Error on loading setting transformer " + transformerName, e);
                        }
                    } else {
                        try {
                            ITransformer transformer = Transformers.getTransformers().get(transformerName).getConstructor().newInstance();
                            group.addTransformer(transformer);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                 NoSuchMethodException e) {
                            MountManipulator.LOGGER.error("Error on loading transformer " + transformerName, e);
                        }
                    }
                }
                this.transformersGroups.put(name, group);
            }
        }

        this.transformersGroups.forEach((key, value) -> MountManipulator.LOGGER.info("Name: " + key + ", Transformers: " + Arrays.toString(value.getTransformers().toArray())));
    }

    @Override
    public void saveConfiguration() {
        ConfigurationSection groupsSection = this.createSection("groups");
        for (Map.Entry<String, TransformersGroup> entry : this.transformersGroups.entrySet()) {
            ConfigurationSection groupSection = groupsSection.createSection(entry.getKey());

            for (ITransformer transformer : entry.getValue().getTransformers()) {
                if (transformer instanceof ISettingTransformer) {
                    ((ISettingTransformer) transformer).saveSettings(groupSection.createSection(transformer.getName()));
                } else {
                    groupSection.createSection(transformer.getName());
                }
            }
        }

        try {
            super.saveConfiguration();
        } catch (IOException e) {
            MountManipulator.LOGGER.error("Error on saving configuration", e);
        }
    }

    public HashMap<String, TransformersGroup> getTransformersGroups() {
        return transformersGroups;
    }
}
