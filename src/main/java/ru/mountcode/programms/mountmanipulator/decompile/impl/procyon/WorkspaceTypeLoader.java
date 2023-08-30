package ru.mountcode.programms.mountmanipulator.decompile.impl.procyon;

import com.strobel.assembler.metadata.Buffer;
import com.strobel.assembler.metadata.ITypeLoader;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.workspace.Workspace;

/**
 * Type loader that pulls classes from a {@link Workspace}.
 *
 * @author xDark
 */
public final class WorkspaceTypeLoader implements ITypeLoader {
    private final Workspace workspace;

    /**
     * @param workspace Active workspace.
     */
    public WorkspaceTypeLoader(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public boolean tryLoadType(String internalName, Buffer buffer) {
        ClassInfo info = workspace.getClasspath().classes().get(internalName);
        if (info == null) {
            return false;
        }
        buffer.position(0);
        byte[] data = info.bytes();
        buffer.putByteArray(data, 0, data.length);
        buffer.position(0);
        return true;
    }
}
