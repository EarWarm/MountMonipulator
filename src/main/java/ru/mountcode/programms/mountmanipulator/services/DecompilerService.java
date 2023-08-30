package ru.mountcode.programms.mountmanipulator.services;

import ru.mountcode.programms.mountmanipulator.api.registry.Registry;
import ru.mountcode.programms.mountmanipulator.api.registry.RegistryKeys;
import ru.mountcode.programms.mountmanipulator.decompile.impl.procyon.ProcyonDecompiler;

public class DecompilerService {

    public static void register() {
        Registry.register(RegistryKeys.DECOMPILER, ProcyonDecompiler.IDENTIFIER, new ProcyonDecompiler());
    }
}
