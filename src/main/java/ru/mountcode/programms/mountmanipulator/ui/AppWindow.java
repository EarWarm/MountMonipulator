package ru.mountcode.programms.mountmanipulator.ui;

public class AppWindow {

    private static AppWindow instance;

    //Init gui
    public AppWindow() {
        instance = this;
    }

    public void initialize() {

    }

    public static AppWindow getInstance() {
        return instance != null ? instance : new AppWindow();
    }
}
