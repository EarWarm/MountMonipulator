package ru.mountcode.programms.mountmanipulator.ui.controls;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.lang.Lang;
import ru.mountcode.programms.mountmanipulator.ui.AppWindow;
import ru.mountcode.programms.mountmanipulator.utils.JarUtils;

import java.io.File;
import java.io.IOException;

public class TopMenu extends MenuBar {

    private static final FileChooser exportPrimary = new FileChooser();

    private static final FileChooser.ExtensionFilter javaProgramFilter = new FileChooser.ExtensionFilter("Java programs & archives", "*.jar", "*.zip");

    public TopMenu() {
        Menu workspaceMenu = new Menu(Lang.translatable("workspace.menu"));

        MenuItem resetWorkspace = new MenuItem(Lang.translatable("workspace.menu.reset"));
        resetWorkspace.setOnAction((event) -> MountManipulator.getInstance().getWorkspace().reset());
        MenuItem saveAs = new MenuItem(Lang.translatable("workspace.menu.save.as"));
        saveAs.setOnAction(event -> {
            if (MountManipulator.getInstance().getWorkspace().getJarObject() == null) {
                return;
            }

            exportPrimary.setInitialFileName(MountManipulator.getInstance().getWorkspace().getJarObject().name());
            File exportLocation = exportPrimary.showSaveDialog(AppWindow.getInstance().getPrimaryStage());
            if (exportLocation != null) {
                if (!exportLocation.getName().endsWith(".jar") && !exportLocation.getName().endsWith(".zip")) {
                    exportLocation = new File(exportLocation, ".jar");
                }
                try {
                    JarUtils.writeJar(MountManipulator.getInstance().getWorkspace().getJarObject(), exportLocation);
                } catch (IOException e) {
                    // TODO: Log not implemented
                    throw new RuntimeException(e);
                }
            }
        });

        workspaceMenu.getItems().addAll(resetWorkspace, saveAs);
        this.getMenus().add(workspaceMenu);
    }

    static {
        exportPrimary.setTitle(Lang.translatable("filechooser.export.program"));
        exportPrimary.getExtensionFilters().add(javaProgramFilter);
        exportPrimary.setSelectedExtensionFilter(javaProgramFilter);
    }
}
