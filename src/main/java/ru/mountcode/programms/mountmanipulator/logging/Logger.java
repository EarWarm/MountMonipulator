package ru.mountcode.programms.mountmanipulator.logging;

public class Logger {

    public void info(String message) {
        print("INFO ", message);
    }

    public void error(String message, Throwable throwable) {
        print("ERROR", message, throwable);
    }

    public void error(String message) {
        print("ERROR", message);
    }

    public void warning(String message) {
        print("WARN ", message);
    }

    private void print(String name, String message, Throwable throwable) {
        System.out.println("[" + name + "]: " + message);
        throwable.printStackTrace();
    }

    private void print(String name, String message) {
        System.out.println("[" + name + "]: " + message);
    }
}
