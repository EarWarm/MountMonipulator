package ru.mountcode.programms.mountmanipulator.decompile.impl.procyon;

import com.strobel.Procyon;
import com.strobel.assembler.metadata.*;
import com.strobel.decompiler.DecompilationOptions;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;
import ru.mountcode.programms.mountmanipulator.api.Identifier;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.decompile.DecompileOption;
import ru.mountcode.programms.mountmanipulator.decompile.Decompiler;
import ru.mountcode.programms.mountmanipulator.workspace.Workspace;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Procyon decompiler.
 *
 * @author xDark
 */
public class ProcyonDecompiler extends Decompiler {

    public static final Identifier IDENTIFIER = new Identifier("Procyon");

    public ProcyonDecompiler() {
        super("Procyon", Procyon.version());
    }

    @Override
    protected String decompileImpl(Map<String, DecompileOption<?>> options, Workspace workspace, ClassInfo classInfo) {
        ITypeLoader loader = new CompositeTypeLoader(
                new TargetedTypeLoader(classInfo.name(), applyPreInterceptors(classInfo.bytes())),
                new WorkspaceTypeLoader(workspace)
        );
        DecompilerSettings settings = DecompilerSettings.javaDefaults();
        settings.setForceExplicitImports(true);
        settings.setTypeLoader(loader);
        MetadataSystem system = new MetadataSystem(loader);
        TypeReference ref = system.lookupType(classInfo.name());
        DecompilationOptions decompilationOptions = new DecompilationOptions();
        decompilationOptions.setSettings(settings);
        StringWriter writer = new StringWriter();
        settings.getLanguage().decompileType(ref.resolve(), new PlainTextOutput(writer), decompilationOptions);
        return writer.toString();
    }

    @Override
    protected Map<String, DecompileOption<?>> createDefaultOptions() {
        // TODO: Populate procyon options from 'DecompilerSettings'
        return new HashMap<>();
    }

    /**
     * Type loader to load a single class file.
     * Used as the first loader within a {@link CompositeTypeLoader} such that it overrides any
     * following type loader that could also procure the same class info.
     */
    private static final class TargetedTypeLoader implements ITypeLoader {
        private final String name;
        private final byte[] data;

        private TargetedTypeLoader(String name, byte[] data) {
            this.name = name;
            this.data = data;
        }

        @Override
        public boolean tryLoadType(String internalName, Buffer buffer) {
            if (internalName.equals(name)) {
                byte[] data = this.data;
                buffer.position(0);
                buffer.putByteArray(data, 0, data.length);
                buffer.position(0);
                return true;
            }
            return false;
        }
    }
}
