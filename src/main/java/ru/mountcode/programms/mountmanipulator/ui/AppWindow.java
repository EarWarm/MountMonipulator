package ru.mountcode.programms.mountmanipulator.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class AppWindow extends Application {

    private static AppWindow instance;

    //Init gui
    public AppWindow() {
        instance = this;
    }

    @Override
    public void start(Stage primaryStage) {

    }
//Инит гуи
    public void initialize() {

    }

    public static AppWindow getInstance() {
        return instance != null ? instance : new AppWindow();
    }
}
