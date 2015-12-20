import com.ote.app.model.FeatureParser;
import com.ote.app.view.FeaturePresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


    private static final String STANDARD_FEATURE =
            "Feature: Feature View Management\r\n" +
                    "\tIn order to test the feature view\r\n" +
                    "\tAs a user\r\n" +
                    "\tI want to create new feature and update data\r\n";

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlFeatureDisplayLoader = new FXMLLoader(ClassLoader.getSystemResource("Feature.Display.fxml"));
        Parent featureDisplayView = fxmlFeatureDisplayLoader.load();

        FXMLLoader fxmlFeatureEditLoader = new FXMLLoader(ClassLoader.getSystemResource("Feature.Edit.fxml"));
        Parent featureEditView = fxmlFeatureEditLoader.load();

        VBox pane = new VBox();
        pane.setPrefSize(800,240);
        pane.getChildren().addAll(featureDisplayView, featureEditView);

        stage.setScene(new Scene(pane));
        stage.setTitle("Cucumber Editor");
        stage.setWidth(800);
        stage.setHeight(240);
        stage.show();

        FeaturePresenter.getInstance().featureProperty().set(FeatureParser.parseFeature(STANDARD_FEATURE));
    }

}
