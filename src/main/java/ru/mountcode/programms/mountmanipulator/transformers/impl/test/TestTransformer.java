package ru.mountcode.programms.mountmanipulator.transformers.impl.test;

import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.programms.mountmanipulator.transformers.settings.ISettingTransformer;
import ru.mountcode.programms.mountmanipulator.transformers.settings.SettingValue;

import java.util.HashMap;

public class TestTransformer implements ISettingTransformer {

    @SettingValue(section = "debug-name", name = "Отладочное название", description = "Имя трансформера в логе работы")
    public String debugName = "TestTransformer";

    @Override
    public void transform(ClassNode classNode, HashMap<String, ClassNode> classes, HashMap<String, ClassNode> classpath) {
        System.out.println(debugName + " >> выполнено");
    }

    @Override
    public String getName() {
        return "TestTransformer";
    }
}
