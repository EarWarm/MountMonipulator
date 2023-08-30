package ru.mountcode.programms.mountmanipulator.ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.mountcode.programms.mountmanipulator.ui.listeners.ExitListener;
import ru.mountcode.programms.mountmanipulator.ui.panes.AppPane;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class AppWindow extends Application {

    private static AppWindow instance;

    private Stage primaryStage;
    private AppPane appPane;

    public AppWindow() {
        instance = this;
    }

    public static AppWindow getInstance() {
        return instance != null ? instance : new AppWindow();
    }

    public static URL getResourceUrl(String resource) {
        return AppWindow.class.getResource("/assets/" + resource);
    }

    public static InputStream getResourceStream(String resource) {
        return AppWindow.class.getResourceAsStream("/assets/" + resource);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
//        Application.setUserAgentStylesheet("/assets/styles/theme_dark.css");
        primaryStage.setTitle("MountManipulator");
        this.appPane = new AppPane();

        primaryStage.setScene(getBoundedScene(this.appPane));
        primaryStage.centerOnScreen();
        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(new ExitListener());
        primaryStage.getIcons().add(new Image(getResourceStream("icons/window/favicon.png")));
        primaryStage.show();
    }

    public AppPane getAppPane() {
        return appPane;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private Scene getBoundedScene(Parent root) {
        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;

        return new Scene(root, width, height);
    }
}
