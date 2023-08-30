package ru.mountcode.programms.mountmanipulator.decompile;

import ru.mountcode.programms.mountmanipulator.api.tools.ToolResult;

/**
 * Decompile result wrapper.
 *
 * @author Matt Coley
 */
public class DecompileResult extends ToolResult<Decompiler, String> {

    /**
     * @param decompiler     Decompiler responsible for the result.
     * @param decompiledText Decompiled code of the class.
     */
    public DecompileResult(Decompiler decompiler, String decompiledText) {
        this(decompiler, decompiledText, null);
    }

    /**
     * @param decompiler Decompiler responsible for the result.
     * @param exception  Exception thrown when attempting to decompile.
     */
    public DecompileResult(Decompiler decompiler, Exception exception) {
        this(decompiler, null, exception);
    }

    private DecompileResult(Decompiler decompiler, String decompiledText, Exception exception) {
        super(decompiler, decompiledText, exception);
    }
}
