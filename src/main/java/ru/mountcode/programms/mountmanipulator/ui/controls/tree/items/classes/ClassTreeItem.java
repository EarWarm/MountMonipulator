package ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.classes;

import javafx.scene.control.TreeItem;
import org.jetbrains.annotations.NotNull;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.FolderTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.utils.FXUtils;

public class ClassTreeItem extends TreeItem<String> implements Comparable<TreeItem<String>> {

    private final ClassInfo classInfo;

    public ClassTreeItem(ClassInfo classInfo) {
        super(classInfo.localName(), FXUtils.getClassIcon(classInfo.classNode()));
        this.classInfo = classInfo;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    @Override
    public int compareTo(@NotNull TreeItem<String> o) {
        if (o instanceof FolderTreeItem) {
            return 1;
        }

        if (this.getValue() == null || o.getValue() == null) {
            return 0;
        }

        return this.getValue().compareTo(o.getValue());
    }
}
