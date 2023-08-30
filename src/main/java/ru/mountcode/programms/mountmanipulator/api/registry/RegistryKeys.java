package ru.mountcode.programms.mountmanipulator.api.registry;

import ru.mountcode.programms.mountmanipulator.decompile.Decompiler;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;

public class RegistryKeys {

    public static final RegistryKey<Class<? extends ITransformer>> TRANSFORMER = new RegistryKey<>();
    public static final RegistryKey<Decompiler> DECOMPILER = new RegistryKey<>();
}
