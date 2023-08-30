package ru.mountcode.programms.mountmanipulator.transformers.impl.test;

import ru.mountcode.programms.mountmanipulator.api.Identifier;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.transformers.ITransformer;

import java.util.HashMap;

public class DeleteTransformer implements ITransformer {

    public static final Identifier IDENTIFIER = new Identifier("Test", "DeleteTransformer");

    @Override
    public void transform(HashMap<String, ClassInfo> classes, HashMap<String, ClassInfo> classpath) {
        classes.remove("ru/mountcode/mods/moreequipment/MoreEquipment");
    }

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }
}
