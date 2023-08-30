package ru.mountcode.programms.mountmanipulator.services;

import ru.mountcode.programms.mountmanipulator.api.registry.Registry;
import ru.mountcode.programms.mountmanipulator.api.registry.RegistryKeys;
import ru.mountcode.programms.mountmanipulator.transformers.impl.optimizations.DupPopTransformer;
import ru.mountcode.programms.mountmanipulator.transformers.impl.test.DeleteTransformer;
import ru.mountcode.programms.mountmanipulator.transformers.impl.test.TestTransformer;

public class TransformerService {

    public static void register() {
        Registry.register(RegistryKeys.TRANSFORMER, DupPopTransformer.IDENTIFIER, DupPopTransformer.class);
        Registry.register(RegistryKeys.TRANSFORMER, TestTransformer.IDENTIFIER, TestTransformer.class);
        Registry.register(RegistryKeys.TRANSFORMER, DeleteTransformer.IDENTIFIER, DeleteTransformer.class);
    }
}
