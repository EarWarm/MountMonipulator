package ru.mountcode.programms.mountmanipulator.transformers.impl.test;

import ru.mountcode.programms.mountmanipulator.api.Identifier;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;
import ru.mountcode.programms.mountmanipulator.transformers.settings.ISettingTransformer;
import ru.mountcode.programms.mountmanipulator.transformers.settings.SettingValue;

import java.util.HashMap;

public class TestTransformer implements ISettingTransformer {

    public static final Identifier IDENTIFIER = new Identifier("Test", "TestTransformer");

    @SettingValue(section = "debug-name", name = "Отладочное название", description = "Имя трансформера в логе работы")
    public String debugName = "TestTransformer";

    @Override
    public void transform(HashMap<String, ClassInfo> classes, HashMap<String, ClassInfo> classpath) {
        System.out.println(debugName + " >> выполнено");
    }

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }
}
