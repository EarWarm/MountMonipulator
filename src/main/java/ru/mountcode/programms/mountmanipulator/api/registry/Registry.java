package ru.mountcode.programms.mountmanipulator.api.registry;

import ru.mountcode.programms.mountmanipulator.api.Identifier;

public class Registry {

    public static <V, T extends V> void register(RegistryKey<V> key, Identifier identifier, T entry) {
        key.register(identifier, entry);
    }
}
