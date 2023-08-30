package ru.mountcode.programms.mountmanipulator.decompile;

/**
 * Used to allow interception of bytecode passed to decompilers.
 *
 * @author Matt Coley
 */
public interface PreDecompileInterceptor {
    /**
     * @param code
     * 		Original bytecode.
     *
     * @return Modified bytecode.
     */
    byte[] apply(byte[] code);
}
