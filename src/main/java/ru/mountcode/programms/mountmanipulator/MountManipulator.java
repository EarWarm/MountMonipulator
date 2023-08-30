package ru.mountcode.programms.mountmanipulator;

import ru.mountcode.programms.mountmanipulator.configuration.Configuration;
import ru.mountcode.programms.mountmanipulator.helpers.IOHelper;
import ru.mountcode.programms.mountmanipulator.lang.Lang;
import ru.mountcode.programms.mountmanipulator.logging.Logger;
import ru.mountcode.programms.mountmanipulator.services.DecompilerService;
import ru.mountcode.programms.mountmanipulator.services.TransformerService;
import ru.mountcode.programms.mountmanipulator.ui.AppWindow;
import ru.mountcode.programms.mountmanipulator.workspace.Workspace;
import ru.mountcode.yaml.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class MountManipulator {

    public static final Logger LOGGER = new Logger();

    private static MountManipulator instance;
    private final Workspace workspace;
    private final Configuration configuration;

    public MountManipulator() {
        instance = this;

        this.workspace = new Workspace();
        this.configuration = new Configuration();
    }

    public static MountManipulator getInstance() {
        return instance == null ? new MountManipulator() : instance;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void run() {
        TransformerService.register();
        DecompilerService.register();

        // Инициализация конфигурации
        this.configuration.setFile(new File(IOHelper.getWorkingDirectory(), "config.yml"));
        this.configuration.setDefault(MountManipulator.class.getResourceAsStream("/config.yml"));
        try {
            this.configuration.initialize();
        } catch (IOException | InvalidConfigurationException e) {
            LOGGER.error("Error on initializing configuration", e);
        }

        Lang.getInstance();


        // UI
        AppWindow.launch(AppWindow.class);
    }
}
