import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("Feature.fxml"));
        Parent featureEditView = fxmlLoader.load();

        /*Pane pane = new Pane();
        pane.getChildren().addAll(featureEditView);*/

        stage.setScene(new Scene(featureEditView));
        stage.setTitle("Cucumber Editor");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

}
