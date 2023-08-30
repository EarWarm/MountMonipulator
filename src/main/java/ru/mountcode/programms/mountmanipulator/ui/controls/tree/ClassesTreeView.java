package ru.mountcode.programms.mountmanipulator.ui.controls.tree;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.model.ClassCollection;
import ru.mountcode.programms.mountmanipulator.ui.AppWindow;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.classes.ClassTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.classes.PackageTreeItem;

import java.util.Map;

public class ClassesTreeView extends TreeView<String> {

    public ClassesTreeView() {
        this.setShowRoot(false);
        this.setCellFactory(param -> new ClassesTreeCell());

        this.setOnMouseClicked(this::onMouseClicked);
    }

    public void fillTree(ClassCollection classCollection) {
        PackageTreeItem rootItem = new PackageTreeItem();
        for (Map.Entry<String, ClassInfo> entry : classCollection.classes().entrySet()) {
            rootItem.addElement(entry.getKey(), entry.getValue());
        }
        for (TreeItem<String> child : rootItem.getChildren()) {
            if (child instanceof PackageTreeItem folder) {
                folder.group(folder);
                folder.sort(folder);
            }
        }
        this.setRoot(rootItem);
    }

    public void onMouseClicked(MouseEvent event) {
        if (this.getSelectionModel().isEmpty()) {
            return;
        }

        if (event.getButton() != MouseButton.PRIMARY) {
            return;
        }

        if (event.getClickCount() >= 2) {
            TreeItem<String> treeItem = this.getSelectionModel().getSelectedItem();
            if (treeItem.isLeaf() && treeItem instanceof ClassTreeItem classTreeItem) {
                AppWindow.getInstance().getAppPane().getWorkspacePane().getCodePane().requestDecompile(classTreeItem.getClassInfo());
            }
        }
    }
}
