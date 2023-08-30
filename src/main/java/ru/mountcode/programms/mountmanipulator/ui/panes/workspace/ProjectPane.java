package ru.mountcode.programms.mountmanipulator.ui.panes.workspace;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;

public class ProjectPane extends SplitPane {

    private final ClassesPane classesPane;
    private final TransformersPane transformersPane;

    public ProjectPane() {
        this.setOrientation(Orientation.VERTICAL);
        this.setDividerPositions(0.666);
        this.classesPane = new ClassesPane();
        this.transformersPane = new TransformersPane();

        this.getItems().addAll(this.classesPane, this.transformersPane);
    }

    public ClassesPane getClassesPane() {
        return classesPane;
    }

    public TransformersPane getTransformersPane() {
        return transformersPane;
    }
}
