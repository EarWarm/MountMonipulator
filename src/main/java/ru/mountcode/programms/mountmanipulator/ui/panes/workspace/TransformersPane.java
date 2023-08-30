package ru.mountcode.programms.mountmanipulator.ui.panes.workspace;

import javafx.scene.layout.BorderPane;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.TransformersTreeView;

public class TransformersPane extends BorderPane {

    private final TransformersTreeView treeView;

    public TransformersPane() {
        this.treeView = new TransformersTreeView();

        this.setCenter(this.treeView);
    }
}
