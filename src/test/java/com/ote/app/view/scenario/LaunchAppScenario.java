package com.ote.app.view.scenario;

import com.ote.app.view.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Olivier on 17/12/2015.
 */
public class LaunchAppScenario extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(new Scene(ViewFactory.getInstance().createScenario()));
        stage.setTitle("Cucumber Editor");
        stage.setWidth(800);
        stage.setHeight(240);
        stage.show();
    }

}
