package ru.mountcode.programms.mountmanipulator.model;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;

public record ClassCollection(HashMap<String, ClassNode> classes) {

    @Nullable
    @Override
    public HashMap<String, ClassNode> classes() {
        return classes;
    }

    public void addClass(String name, ClassNode classNode) {
        this.classes.put(name, classNode);
    }

    public void addClasses(ClassCollection classCollection) {
        this.classes.putAll(classCollection.classes());
    }
}
