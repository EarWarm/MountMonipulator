package ru.mountcode.programms.mountmanipulator.ui.utils;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.lang.Lang;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;
import ru.mountcode.programms.mountmanipulator.ui.AppWindow;
import ru.mountcode.programms.mountmanipulator.ui.controls.popup.TransformersChoosePopup;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers.GroupTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers.TransformerTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.resources.Icons;

import java.util.ArrayList;
import java.util.List;

public class ContextBuilder {

    private final List<MenuItem> items = new ArrayList<>();

    private ContextBuilder() {
    }

    public static ContextBuilder builder() {
        return new ContextBuilder();
    }

    public ContextBuilder ofTransformer(TransformerTreeItem treeItem) {
        if (MountManipulator.getInstance().getWorkspace().canExecuted()) {
            MenuItem executeItem = new MenuItem(Lang.translatable("execute"), Icons.iconRun());
            executeItem.setOnAction(event -> MountManipulator.getInstance().getWorkspace().execute(treeItem.getTransformer()));
            this.items.add(executeItem);
        }
        return this;
    }

    public ContextBuilder ofTransformersGroup(GroupTreeItem treeItem) {
        if (MountManipulator.getInstance().getWorkspace().canExecuted()) {
            MenuItem executeItem = new MenuItem(Lang.translatable("execute"), Icons.iconRun());
            executeItem.setOnAction(event -> MountManipulator.getInstance().getWorkspace().execute(treeItem.getGroup()));
            this.items.add(executeItem);
        }
        MenuItem newItem = new MenuItem(Lang.translatable("new"));
        newItem.setOnAction(event -> {
            TransformersChoosePopup dialog = new TransformersChoosePopup(treeItem);
            dialog.show(AppWindow.getInstance().getPrimaryStage());
        });
        this.items.add(newItem);
        return this;
    }

    public ContextBuilder ofRootGroups(TreeItem<String> rootItem) {
        MenuItem newItem = new MenuItem(Lang.translatable("new"));
        newItem.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(Lang.translatable("popup.group.create"));

            String name;
            do {
                dialog.getEditor().setText("");
                dialog.showAndWait();
                name = dialog.getResult();
            } while(name != null && !name.isEmpty() && !name.isBlank() && MountManipulator.getInstance().getConfiguration().getTransformersGroups().containsKey(name));

            if (name != null && !name.isEmpty() && !name.isBlank()) {
                TransformersGroup group = new TransformersGroup(name);
                MountManipulator.getInstance().getConfiguration().getTransformersGroups().put(name, group);
                MountManipulator.getInstance().getConfiguration().saveConfiguration();
                rootItem.getChildren().add(new GroupTreeItem(group));
            }
        });
        this.items.add(newItem);

        return this;
    }

    public ContextMenu build() {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(items);
        return contextMenu;
    }
}
