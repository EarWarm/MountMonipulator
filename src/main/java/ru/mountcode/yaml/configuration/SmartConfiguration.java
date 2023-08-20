package ru.mountcode.yaml.configuration;

import com.google.common.io.Files;
import org.apache.commons.lang3.Validate;
import ru.mountcode.yaml.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SmartConfiguration extends YamlConfiguration {
    private InputStream defaultFile = null;
    private File file = null;

    public void setDefault(InputStream defaultFile) {
        this.defaultFile = defaultFile;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFile(String file) {
        this.file = new File(file);
    }

    public void initialize() throws IOException, InvalidConfigurationException {
        Validate.notNull(this.file, "Configuration file cannot be null");
        if (this.file.exists()) {
            this.load(this.file);
        } else {
            this.generateFile();
        }

    }

    private void generateFile() throws IOException, InvalidConfigurationException {
        if (this.defaultFile == null) {
            this.options().copyDefaults(true);
            this.saveConfiguration();
        } else {
            Files.createParentDirs(this.file);
            java.nio.file.Files.copy(this.defaultFile, this.file.toPath());
            this.load(this.file);
        }
    }

    public void reloadConfiguration() throws IOException, InvalidConfigurationException {
        this.initialize();
    }

    public void saveConfiguration() throws IOException {
        this.save(this.file);
    }
}
