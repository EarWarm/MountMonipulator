package ru.mountcode.programms.mountmanipulator.transformers;

import ru.mountcode.programms.mountmanipulator.api.Identifier;
import ru.mountcode.programms.mountmanipulator.code.ClassInfo;

import java.util.HashMap;

public interface ITransformer {

    void transform(HashMap<String, ClassInfo> classes, HashMap<String, ClassInfo> classpath);

    Identifier getIdentifier();
}
