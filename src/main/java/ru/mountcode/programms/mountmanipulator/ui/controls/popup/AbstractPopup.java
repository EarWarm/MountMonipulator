package ru.mountcode.programms.mountmanipulator.ui.controls.popup;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class AbstractPopup extends Stage {

    public AbstractPopup() {
        this.initModality(Modality.APPLICATION_MODAL);
    }


}
