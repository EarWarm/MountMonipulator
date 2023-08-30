package ru.mountcode.programms.mountmanipulator.api.registry;

import ru.mountcode.programms.mountmanipulator.api.Identifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RegistryKey<T> {

    private final HashMap<Identifier, T> values;

    public RegistryKey() {
        values = new HashMap<>();
    }

    void register(Identifier identifier, T entry) {
        if (this.values.containsKey(identifier)) {
            throw new RuntimeException("Entry already exists on key " + identifier.toString());
        }

        this.values.put(identifier, entry);
    }

    public T getEntry(Identifier identifier) {
        return values.get(identifier);
    }

    public Set<Map.Entry<Identifier, T>> getEntries() {
        return this.values.entrySet();
    }

    public Collection<T> getValues() {
        return this.values.values();
    }

    public Set<Identifier> getKeys() {
        return this.values.keySet();
    }

    public boolean contains(Identifier identifier) {
        return this.values.containsKey(identifier);
    }
}
