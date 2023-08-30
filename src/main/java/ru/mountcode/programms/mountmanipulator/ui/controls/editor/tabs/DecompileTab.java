package ru.mountcode.programms.mountmanipulator.ui.controls.editor.tabs;

import ru.mountcode.programms.mountmanipulator.MountManipulator;
import ru.mountcode.programms.mountmanipulator.api.Identifier;
import ru.mountcode.programms.mountmanipulator.api.registry.RegistryKeys;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.decompile.DecompileResult;
import ru.mountcode.programms.mountmanipulator.decompile.Decompiler;
import ru.mountcode.programms.mountmanipulator.events.WorkspaceEvents;
import ru.mountcode.programms.mountmanipulator.ui.controls.editor.EditorArea;
import ru.mountcode.programms.mountmanipulator.ui.language.Languages;

public class DecompileTab extends InteractiveTab {

    private final ClassInfo classInfo;
    private final EditorArea editorArea;
    private Decompiler decompiler;

    public DecompileTab(ClassInfo classInfo) {
        super(classInfo.localName());
        this.classInfo = classInfo;
        this.editorArea = new EditorArea(Languages.JAVA);
        this.editorArea.setEditable(false);

        this.decompiler = RegistryKeys.DECOMPILER.getEntry(MountManipulator.getInstance().getConfiguration().getDecompiler());
        this.decompile();

        this.setContent(this.editorArea);

        WorkspaceEvents.STOP_EXECUTING.register(() -> {
            if (MountManipulator.getInstance().getWorkspace().getJarObject().classCollection().classes().containsKey(classInfo.name())) {
                this.decompile();
            } else {
                this.getTabPane().getTabs().remove(this);
            }
        });
    }

    public void decompile() {
        DecompileResult result = this.decompiler.decompile(MountManipulator.getInstance().getWorkspace(), this.classInfo);
        if (result.wasSuccess()) {
            this.editorArea.setText(result.getValue());
        } else {
            result.getException().printStackTrace();
            this.editorArea.setText("Decompilation failed");
        }
    }

    public void changeDecompiler(Identifier identifier) {
        this.decompiler = RegistryKeys.DECOMPILER.getEntry(identifier);
        this.decompile();
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public EditorArea getEditorArea() {
        return editorArea;
    }
}
