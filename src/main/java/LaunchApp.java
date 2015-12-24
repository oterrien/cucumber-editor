import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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

        stage.setScene(new Scene(new Pane(new Label("Nothing Yet")) ));
        stage.setTitle("Cucumber Editor");
        stage.setWidth(800);
        stage.setHeight(240);
        stage.show();
    }

}
