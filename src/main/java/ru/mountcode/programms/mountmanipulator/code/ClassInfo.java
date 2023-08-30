package ru.mountcode.programms.mountmanipulator.code;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public record ClassInfo(ClassNode classNode) {

    public byte[] bytes() {
        ClassWriter classWriter = new ClassWriter(0);
        this.classNode.accept(classWriter);

        return classWriter.toByteArray();
    }

    public String localName() {
        return classNode.name.substring(classNode.name.lastIndexOf("/") + 1);
    }

    public String name() {
        return classNode.name;
    }

    public int version() {
        return classNode.version;
    }
}
