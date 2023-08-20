package ru.mountcode.programms.mountmanipulator.transformers;

import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;

public interface ITransformer {

    void transform(ClassNode classNode, HashMap<String, ClassNode> classes, HashMap<String, ClassNode> classpath);

    String getName();
}
