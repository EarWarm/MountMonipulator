package ru.mountcode.programms.mountmanipulator.ui.controls.tree;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.classes.ClassTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.classes.PackageTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.resources.Icons;

public class ClassesTreeCell extends TreeCell<String> {

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            this.setGraphic(null);
            this.setText(null);
            this.setContextMenu(null);
            return;
        }

        TreeItem<String> treeItem = this.getTreeItem();
        if (treeItem instanceof ClassTreeItem classTreeItem) {
            this.setText(classTreeItem.getValue());
            this.setGraphic(classTreeItem.getGraphic());
        } else if (treeItem instanceof PackageTreeItem packageTreeItem) {
            this.setText(packageTreeItem.getValue());
            this.setGraphic(Icons.iconPackage());
        }
    }
}
