package ru.mountcode.programms.mountmanipulator.ui.controls.popup;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import ru.mountcode.programms.mountmanipulator.api.Identifier;
import ru.mountcode.programms.mountmanipulator.api.registry.RegistryKeys;
import ru.mountcode.programms.mountmanipulator.lang.Lang;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;
import ru.mountcode.programms.mountmanipulator.transformers.TransformersGroup;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers.GroupTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.controls.tree.items.transformers.TransformerTreeItem;
import ru.mountcode.programms.mountmanipulator.ui.resources.Icons;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class TransformersChoosePopup extends Popup {

    public TransformersChoosePopup(GroupTreeItem groupTreeItem) {
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(5, 20, 5, 20));
        mainPane.setStyle("-fx-border-radius: 20; -fx-background-radius: 20; -fx-background-color: #fff");

        Label label = new Label(Lang.translatable("popup.transformers.choose"));
        label.setAlignment(Pos.CENTER);
        label.setPrefSize(300, 20);
        mainPane.setTop(label);

        ChooseTreeView treeView = new ChooseTreeView();
        treeView.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (treeView.getSelectionModel().isEmpty()) {
                    return;
                }

                for (TreeItem<String> treeItem : treeView.getSelectionModel().getSelectedItems()) {
                    if (!(treeItem instanceof ChooseTransformerTreeItem chooseItem)) {
                        continue;
                    }
                    TransformersGroup group = groupTreeItem.getGroup();
                    try {
                        ITransformer transformer = RegistryKeys.TRANSFORMER.getEntry(chooseItem.getIdentifier()).getConstructor().newInstance();
                        group.addTransformer(transformer);
                        groupTreeItem.getChildren().add(new TransformerTreeItem(transformer));
                    } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                             IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.hide();
            }
        });
        mainPane.setCenter(treeView);

        this.getContent().add(mainPane);
        this.setAutoHide(true);
        this.sizeToScene();
    }

    private static class ChooseTreeCell extends TreeCell<String> {

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                this.setText(null);
                this.setGraphic(null);
                return;
            }

            TreeItem<String> treeItem = this.getTreeItem();
            if (treeItem instanceof ChooseGroupTreeItem groupTreeItem) {
                Identifier identifier = groupTreeItem.getIdentifier();
                this.setText(identifier.getNamespace());
                this.setGraphic(Icons.iconFolder());
                this.updateSelected(false);
            } else if (treeItem instanceof ChooseTransformerTreeItem transformerTreeItem) {
                Identifier identifier = transformerTreeItem.getIdentifier();
                this.setText(identifier.getPath());
                this.setGraphic(Icons.iconTransformer());
            }
        }
    }

    private static class ChooseTreeView extends TreeView<String> {

        public ChooseTreeView() {
            HashMap<String, ChooseGroupTreeItem> existsGroups = new HashMap<>();

            TreeItem<String> rootItem = new TreeItem<>();
            for (Identifier identifier : RegistryKeys.TRANSFORMER.getKeys()) {
                String group = identifier.getNamespace();
                if (existsGroups.containsKey(group)) {
                    existsGroups.get(group).getChildren().add(new ChooseTransformerTreeItem(identifier));
                } else {
                    ChooseGroupTreeItem groupTreeItem = new ChooseGroupTreeItem(identifier);
                    groupTreeItem.getChildren().add(new ChooseTransformerTreeItem(identifier));
                    existsGroups.put(group, groupTreeItem);
                    rootItem.getChildren().add(groupTreeItem);
                }
            }

            this.setRoot(rootItem);
            this.setShowRoot(false);
            this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            this.setCellFactory(param -> new ChooseTreeCell());
        }
    }

    private static class ChooseTransformerTreeItem extends TreeItem<String> {

        private final Identifier identifier;

        public ChooseTransformerTreeItem(Identifier identifier) {
            super(identifier.getPath());
            this.identifier = identifier;
        }

        public Identifier getIdentifier() {
            return identifier;
        }
    }

    private static class ChooseGroupTreeItem extends TreeItem<String> {

        private final Identifier identifier;

        public ChooseGroupTreeItem(Identifier identifier) {
            super(identifier.getNamespace());
            this.identifier = identifier;
        }

        public Identifier getIdentifier() {
            return identifier;
        }
    }
}
