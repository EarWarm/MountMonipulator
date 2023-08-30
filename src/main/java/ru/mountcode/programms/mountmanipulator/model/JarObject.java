package ru.mountcode.programms.mountmanipulator.model;

import java.util.HashMap;

public record JarObject(String name, ClassCollection classCollection, HashMap<String, byte[]> extraFiles) {
}
