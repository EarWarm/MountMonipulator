package ru.mountcode.programms.mountmanipulator.ui.controls.editor.tabs;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import ru.mountcode.programms.mountmanipulator.lang.Lang;

public class InteractiveTab extends Tab {

    public InteractiveTab(String name) {
        super(name);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem closeItem = new MenuItem(Lang.translatable("tab.close"));
        closeItem.setOnAction(event -> this.getTabPane().getTabs().remove(this));
        MenuItem closeAllItem = new MenuItem(Lang.translatable("tab.close.all"));
        closeAllItem.setOnAction(event -> this.getTabPane().getTabs().clear());
        MenuItem closeOthersItem = new MenuItem(Lang.translatable("tab.close.others"));
        closeOthersItem.setOnAction(event -> this.getTabPane().getTabs().removeIf(tab -> !tab.equals(this)));
        MenuItem closeLeftItem = new MenuItem(Lang.translatable("tab.close.left"));
        closeLeftItem.setOnAction(event -> this.getTabPane().getTabs().removeIf(tab -> this.getTabPane().getTabs().indexOf(tab) < this.getTabPane().getTabs().indexOf(this)));
        MenuItem closeRightItem = new MenuItem(Lang.translatable("tab.close.right"));
        closeRightItem.setOnAction(event -> this.getTabPane().getTabs().removeIf(tab -> this.getTabPane().getTabs().indexOf(tab) > this.getTabPane().getTabs().indexOf(this)));
        contextMenu.getItems().addAll(closeItem, closeOthersItem, closeAllItem, closeRightItem, closeLeftItem);
        this.setContextMenu(contextMenu);
        contextMenu.setOnShowing(event -> {
            ObservableList<Tab> tabs = this.getTabPane().getTabs();
            if (tabs.size() == 1) {
                closeOthersItem.setVisible(false);
                closeLeftItem.setVisible(false);
                closeRightItem.setVisible(false);
                closeAllItem.setVisible(false);
            } else {
                closeOthersItem.setVisible(true);
                closeAllItem.setVisible(true);

                closeRightItem.setVisible(tabs.indexOf(this) != tabs.size() - 1);
                closeLeftItem.setVisible(tabs.indexOf(this) != 0);
            }
        });
    }
}
