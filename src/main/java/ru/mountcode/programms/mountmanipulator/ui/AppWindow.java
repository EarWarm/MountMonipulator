package ru.mountcode.programms.mountmanipulator.ui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppWindow extends Application {

    private static AppWindow instance;

    //Init gui
    public AppWindow() {
        instance = this;
    }
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("MountManipulation");
        StackPane root = new StackPane();

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.TOP_RIGHT);
        buttonBox.setSpacing(10);


        Button closeButton = new Button();
        closeButton.setPrefSize(30, 30);


        closeButton.setOnMouseClicked((MouseEvent event) -> {
            primaryStage.close();
        });

        buttonBox.getChildren().add(closeButton);

        root.getChildren().addAll(buttonBox);

        Scene scene = new Scene(root, 800.0D, 600.0D);

        scene.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static AppWindow getInstance() {
        return instance != null ? instance : new AppWindow();
    }
}
