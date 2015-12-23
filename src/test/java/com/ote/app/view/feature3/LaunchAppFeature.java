package com.ote.app.view.feature3;

import com.ote.app.model.FeatureParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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

        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("com/ote/app/view/feature3/Feature.fxml"));
        Parent node = fxmlLoader.load();

        stage.setScene(new Scene(node));
        stage.setTitle("Feature Editor");
        stage.setWidth(800);
        stage.setHeight(240);
        stage.show();
    }

}
