package ru.mountcode.programms.mountmanipulator.model;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.jar.Manifest;

public record JarObject(String name, ClassCollection classCollection, HashMap<String, byte[]> extraFiles) {

    @Nullable
    @Override
    public HashMap<String, byte[]> extraFiles() {
        return extraFiles;
    }
}
