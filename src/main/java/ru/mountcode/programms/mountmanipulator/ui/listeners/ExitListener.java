package ru.mountcode.programms.mountmanipulator.ui.listeners;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;
import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.lang.Lang;

public class ExitListener implements EventHandler<WindowEvent> {

    @Override
    public void handle(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
        alert.setTitle(Lang.translatable("confirm"));
        alert.setHeaderText(Lang.translatable("exit.confirm"));
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            MountManipulator.getInstance().getConfiguration().saveConfiguration();
        } else {
            event.consume();
        }
    }
}
