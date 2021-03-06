package com.ote.app.view.feature;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by Olivier on 17/12/2015.
 */
public class LaunchAppFeature extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("com/ote/app/view/feature/Feature.fxml"));
        Parent node = fxmlLoader.load();

        AnchorPane anchorPane = new AnchorPane();
        ScrollPane pane = new ScrollPane();
        pane.setContent(anchorPane);

        anchorPane.getChildren().add(node);
        AnchorPane.setBottomAnchor(node, 0d);
        AnchorPane.setTopAnchor(node, 0d);
        AnchorPane.setRightAnchor(node, 0d);
        AnchorPane.setLeftAnchor(node, 0d);


        stage.setScene(new Scene(pane));
        stage.setTitle("Feature Editor");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

}
