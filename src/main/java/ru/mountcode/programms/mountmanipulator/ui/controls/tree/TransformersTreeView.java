package ru.mountcode.programms.mountmanipulator.ui.controls.tree;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;
import ru.mountcode.programms.mountmanipulator.ui.AppWindow;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.classes.ClassTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers.GroupTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers.TransformerTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.utils.ContextBuilder;

public class TransformersTreeView extends TreeView<String> {

    public TransformersTreeView() {
        this.setShowRoot(false);
        this.setCellFactory(param -> new TransformersTreeCell());

        TreeItem<String> rootItem = new TreeItem<>();
        for (TransformersGroup group : MountManipulator.getInstance().getConfiguration().getTransformersGroups().values()) {
            GroupTreeItem groupItem = new GroupTreeItem(group);

            for (ITransformer transformer : group.getTransformers()) {
                groupItem.getChildren().add(new TransformerTreeItem(transformer));
            }
            rootItem.getChildren().add(groupItem);
        }

        this.setRoot(rootItem);
        this.setContextMenu(ContextBuilder.builder().ofRootGroups(rootItem).build());
    }
}
