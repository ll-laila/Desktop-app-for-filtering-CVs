package com.example.projetjava.Apps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AccueilAppRec extends Application {
    double x,y = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("AccueilViewRec.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {


            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        Scene scene = new Scene(root,1000, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
