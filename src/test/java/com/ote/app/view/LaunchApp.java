package com.ote.app.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Olivier on 17/12/2015.
 */
public class LaunchApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("com/ote/app/view/feature/Feature.fxml"));
        Parent featureNode = fxmlLoader.load();

        fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("com/ote/app/view/scenario/Scenario.fxml"));
        Parent scenarioNode = fxmlLoader.load();

        ScrollPane pane = new ScrollPane();
        AnchorPane anchorPane = new AnchorPane();
        pane.setContent(anchorPane);

        VBox box = new VBox();
        box.getChildren().addAll(featureNode, scenarioNode);

        anchorPane.getChildren().add(box);

        stage.setScene(new Scene(pane));
        stage.setTitle("Cucumber Editor");
        stage.setWidth(800);
        stage.setHeight(800);
        stage.show();
    }

}
