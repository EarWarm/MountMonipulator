package ru.mountcode.programms.mountmanipulator.decompile;

/**
 * Used to allow interception of decompiled code created by decompilers.
 *
 * @author Matt Coley
 */
public interface PostDecompileInterceptor {
    /**
     * @param code
     * 		Decompiled code.
     *
     * @return Filtered decompiled code.
     */
    String apply(String code);
}
