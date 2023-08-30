package ru.mountcode.programms.mountmanipulator.ui.panes.workspace;

import javafx.scene.control.TabPane;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.code.FileInfo;
import ru.mountcode.programms.mountmanipulator.events.WorkspaceEvents;
import ru.mountcode.programms.mountmanipulator.ui.controls.editor.tabs.DecompileTab;

public class ViewPane extends TabPane {

    public ViewPane() {

        WorkspaceEvents.RESET.register(() -> this.getTabs().clear());
        WorkspaceEvents.LOAD_INPUT.register(jarObject -> this.getTabs().clear());
    }

    public void requestDecompile(ClassInfo classInfo) {
        if (this.getTabs().stream().anyMatch(tab -> {
            if (tab instanceof DecompileTab decompileTab) {
                if (decompileTab.getClassInfo().name().equals(classInfo.name())) {
                    this.getSelectionModel().select(tab);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (tab.getText().equalsIgnoreCase(classInfo.localName())) {
                    this.getSelectionModel().select(tab);
                    return true;
                } else {
                    return false;
                }
            }
        })) {

            return;
        }
        DecompileTab decompileTab = new DecompileTab(classInfo);
        this.getTabs().add(decompileTab);
        this.getSelectionModel().select(decompileTab);
    }

    public void requestView(FileInfo fileInfo) {

    }
}
