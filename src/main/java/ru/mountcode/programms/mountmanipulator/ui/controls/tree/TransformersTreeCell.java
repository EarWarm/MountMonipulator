package ru.mountcode.programms.mountmanipulator.ui.controls.tree;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers.GroupTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers.TransformerTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.resources.Icons;
import ru.mountcode.programms.mountmanipulator.ui.utils.ContextBuilder;

import java.util.HashMap;
import java.util.function.Consumer;

public class TransformersTreeCell extends TreeCell<String> {

    private static final HashMap<Class<? extends TreeItem<String>>, Consumer<TransformersTreeCell>> CLASS_TO_THING = new HashMap<>();

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            this.setGraphic(null);
            this.setText(null);
            this.setContextMenu(null);
            return;
        }

        Class<?> itemClass = this.getTreeItem().getClass();
        Consumer<TransformersTreeCell> consumer = CLASS_TO_THING.get(itemClass);
        if (consumer == null) {
            return;
        }

        consumer.accept(this);
    }

    static {
        CLASS_TO_THING.put(GroupTreeItem.class, cell -> {
            GroupTreeItem treeItem = (GroupTreeItem) cell.getTreeItem();

            cell.setGraphic(Icons.iconFolder());
            cell.setText(treeItem.getGroup().getName());
            cell.setContextMenu(ContextBuilder.builder().ofTransformersGroup(treeItem).build());
        });

        CLASS_TO_THING.put(TransformerTreeItem.class, cell -> {
            TransformerTreeItem treeItem = (TransformerTreeItem) cell.getTreeItem();

            cell.setGraphic(Icons.iconTransformer());
            cell.setText(treeItem.getTransformer().getIdentifier().toString());
            cell.setContextMenu(ContextBuilder.builder().ofTransformer(treeItem).build());
        });
    }
}
