package ru.mountcode.programms.mountmanipulator.ui.controls.tree.items;

import javafx.scene.control.TreeItem;
import org.jetbrains.annotations.NotNull;

public class FolderTreeItem extends TreeItem<String> implements Comparable<TreeItem<String>> {

    public FolderTreeItem() {
    }

    public FolderTreeItem(String value) {
        super(value);
    }

    public TreeItem<String> findChild(FolderTreeItem parent, String name) {
        for (TreeItem<String> child : parent.getChildren()) {
            if (child.getValue().equals(name)) {
                return child;
            }
        }
        return null;
    }

    protected FolderTreeItem createFolder(String value) {
        return new FolderTreeItem(value);
    }

    public void addElement(String[] path, TreeItem<String> element) {
        FolderTreeItem currentParent = this;

        for (int i = 0; i < path.length; i++) {
            if (i == path.length - 1) {
                currentParent.getChildren().add(element);
                break;
            }

            TreeItem<String> childFolder = findChild(currentParent, path[i]);
            if (!(childFolder instanceof FolderTreeItem folder)) {
                currentParent.getChildren().add(currentParent = createFolder(path[i]));
            } else {
                currentParent = folder;
            }
        }
    }

    public void group(FolderTreeItem parent) {
        if (parent.getChildren().size() == 1) {
            TreeItem<String> child = parent.getChildren().get(0);
            if (child instanceof FolderTreeItem folder) {
                parent.setValue(parent.getValue() + "." + folder.getValue());
                parent.getChildren().setAll(folder.getChildren());
                group(parent);
            }
        } else {
            for (TreeItem<String> child : parent.getChildren()) {
                if (child instanceof FolderTreeItem folder) {
                    group(folder);
                }
            }
        }
    }

    public void sort(FolderTreeItem parent) {
        parent.getChildren().sort((o1, o2) -> ((Comparable<TreeItem<String>>) o1).compareTo(o2));

        for (TreeItem<String> child : parent.getChildren()) {
            if (child instanceof FolderTreeItem folder) {
                sort(folder);
            }
        }
    }

    @Override
    public int compareTo(@NotNull TreeItem<String> object) {
        if (!(object instanceof FolderTreeItem)) {
            return -1;
        }

        if (this.getValue() == null || object.getValue() == null) {
            return 0;
        }

        return this.getValue().compareTo(object.getValue());
    }
}
