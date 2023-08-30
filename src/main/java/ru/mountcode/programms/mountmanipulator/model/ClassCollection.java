package ru.mountcode.programms.mountmanipulator.model;

import ru.mountcode.programms.mountmanipulator.code.ClassInfo;

import java.util.HashMap;

public record ClassCollection(HashMap<String, ClassInfo> classes) {

    public void addClass(String name, ClassInfo classInfo) {
        this.classes.put(name, classInfo);
    }

    public void addClasses(ClassCollection classCollection) {
        this.classes.putAll(classCollection.classes());
    }

    public void clear() {
        this.classes.clear();
    }
}
