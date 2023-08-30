package ru.mountcode.programms.mountmanipulator.configuration;

import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.api.Identifier;
import ru.mountcode.programms.mountmanipulator.api.registry.RegistryKeys;
import ru.mountcode.programms.mountmanipulator.decompile.impl.procyon.ProcyonDecompiler;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;
import ru.mountcode.programms.mountmanipulator.transformers.settings.ISettingTransformer;
import ru.mountcode.yaml.configuration.ConfigurationSection;
import ru.mountcode.yaml.configuration.InvalidConfigurationException;
import ru.mountcode.yaml.configuration.SmartConfiguration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Configuration extends SmartConfiguration {

    private Identifier decompiler = ProcyonDecompiler.IDENTIFIER;
    private String locale = "ru";
    private final HashMap<String, TransformersGroup> transformersGroups = new HashMap<>();

    @Override
    public void initialize() throws IOException, InvalidConfigurationException {
        super.initialize();

        this.locale = this.getString("locale", this.locale);
        ConfigurationSection decompileSection = this.getConfigurationSection("decompile");
        if (decompileSection != null) {
            this.decompiler = new Identifier(decompileSection.getString("decompiler", this.decompiler.toString()));
        }

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

                    Identifier identifier = new Identifier(transformerSection.getString("transformer-identifier"));
                    if (!RegistryKeys.TRANSFORMER.contains(identifier)) {
                        continue;
                    }

                    try {
                        ITransformer transformer = RegistryKeys.TRANSFORMER.getEntry(identifier).getConstructor().newInstance();
                        if (!(transformer instanceof ISettingTransformer)) {
                            group.addTransformer(transformer);
                            continue;
                        }
                        ((ISettingTransformer) transformer).loadSettings(transformerSection);
                        group.addTransformer(transformer);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        MountManipulator.LOGGER.error("Error on loading transformer " + transformerName, e);
                    }
                }
                this.transformersGroups.put(name, group);
            }
        }

    }

    @Override
    public void saveConfiguration() {
        this.set("locale", this.locale);

        ConfigurationSection decompileSection = this.createSection("decompile");
        decompileSection.set("decompiler", this.decompiler.toString());

        ConfigurationSection groupsSection = this.createSection("groups");
        int i = 0;
        for (Map.Entry<String, TransformersGroup> entry : this.transformersGroups.entrySet()) {
            ConfigurationSection groupSection = groupsSection.createSection(entry.getKey());

            for (ITransformer transformer : entry.getValue().getTransformers()) {
                ConfigurationSection transformerSection = groupSection.createSection(String.valueOf(i));
                transformerSection.set("transformer-identifier", transformer.getIdentifier().toString());
                if (transformer instanceof ISettingTransformer) {
                    ((ISettingTransformer) transformer).saveSettings(transformerSection);
                }
                i++;
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

    public String getLocale() {
        return locale;
    }

    public Identifier getDecompiler() {
        return decompiler;
    }
}
