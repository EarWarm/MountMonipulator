package ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.classes;

import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.FolderTreeItem;

public class PackageTreeItem extends FolderTreeItem {

    public PackageTreeItem(String value) {
        super(value);
    }

    public PackageTreeItem() {
    }

    public void addElement(String fullPath, ClassInfo classInfo) {
        String[] path = fullPath.split("/");
        this.addElement(path, new ClassTreeItem(classInfo));
    }

    @Override
    protected FolderTreeItem createFolder(String value) {
        return new PackageTreeItem(value);
    }
}
