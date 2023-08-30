package ru.mountcode.programms.mountmanipulator.ui.panes;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import ru.mountcode.programms.mountmanipulator.ui.panes.workspace.ProjectPane;
import ru.mountcode.programms.mountmanipulator.ui.panes.workspace.ViewPane;

public class WorkspacePane extends SplitPane {

    private final ViewPane codePane;
    private final ProjectPane projectPane;

    public WorkspacePane() {
        this.codePane = new ViewPane();
        this.projectPane = new ProjectPane();


        this.setOrientation(Orientation.HORIZONTAL);
        this.setDividerPositions(0.333);
        SplitPane.setResizableWithParent(this.projectPane, Boolean.FALSE);

        this.getItems().addAll(this.projectPane, this.codePane);
    }

    public ViewPane getCodePane() {
        return codePane;
    }

    public ProjectPane getProjectPane() {
        return projectPane;
    }
}
