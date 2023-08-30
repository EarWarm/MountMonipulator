package ru.mountcode.programms.mountmanipulator.ui.panes;

import javafx.scene.layout.BorderPane;
import ru.mountcode.programms.mountmanipulator.ui.controls.TopMenu;

public class AppPane extends BorderPane {

    private final TopMenu topMenu;
    private final WorkspacePane workspacePane;

    public AppPane() {
//        this.getStylesheets().add("/assets/styles/theme_dark.css");
        this.getStylesheets().add("/assets/styles/themes/dark.css");
        this.topMenu = new TopMenu();
        this.setTop(this.topMenu);
        this.workspacePane = new WorkspacePane();

        this.setCenter(this.workspacePane);
    }

    public TopMenu getTopMenu() {
        return topMenu;
    }

    public WorkspacePane getWorkspacePane() {
        return workspacePane;
    }
}
