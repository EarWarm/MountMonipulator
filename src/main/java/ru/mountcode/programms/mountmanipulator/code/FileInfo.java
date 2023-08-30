package ru.mountcode.programms.mountmanipulator.code;

public class FileInfo {

    private final String name;
    private final byte[] bytes;

    public FileInfo(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
