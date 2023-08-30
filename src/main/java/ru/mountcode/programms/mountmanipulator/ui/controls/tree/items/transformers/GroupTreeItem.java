package ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers;

import javafx.scene.control.TreeItem;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;

public class GroupTreeItem extends TreeItem<String> {

    private final TransformersGroup group;

    public GroupTreeItem(TransformersGroup group) {
        super(group.getName());
        this.group = group;
    }

    public TransformersGroup getGroup() {
        return group;
    }
}
