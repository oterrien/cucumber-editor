package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.model.FeatureParser;
import com.ote.app.view.feature.FeaturePresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by Olivier on 21/12/2015.
 */
public final class ViewFactory {

    private static final String STANDARD_FEATURE =
            "Feature: Feature View Management\r\n" +
                    "\tIn order to test the feature view\r\n" +
                    "\tAs a user\r\n" +
                    "\tI want to create new feature and update data\r\n";

    private final static ViewFactory INSTANCE = new ViewFactory();

    private ViewFactory() {
    }

    public static ViewFactory getInstance() {
        return INSTANCE;
    }

    public Pane createFeature() throws Exception{

        FXMLLoader fxmlDisplayLoader = new FXMLLoader(ClassLoader.getSystemResource("com/ote/app/view/feature/Feature.Display.fxml"));
        Parent displayView = fxmlDisplayLoader.load();

        FXMLLoader fxmlEditLoader = new FXMLLoader(ClassLoader.getSystemResource("com/ote/app/view/feature/Feature.Edit.fxml"));
        Parent editView = fxmlEditLoader.load();

        VBox pane = new VBox();
        pane.setPrefSize(800,240);
        pane.getChildren().addAll(displayView, editView);

        FeaturePresenter.getInstance().featureProperty().set(FeatureParser.parseFeature(STANDARD_FEATURE));
        FeaturePresenter.getInstance().modeProperty().set(Mode.DISPLAY);

        return pane;
    }


    public Pane createScenario() throws Exception{

        FXMLLoader fxmlDisplayLoader = new FXMLLoader(ClassLoader.getSystemResource("com/ote/app/view/scenario/Scenario.Display.fxml"));
        Parent displayView = fxmlDisplayLoader.load();

        FXMLLoader fxmlEditLoader = new FXMLLoader(ClassLoader.getSystemResource("com/ote/app/view/scenario/Scenario.Edit.fxml"));
        Parent editView = fxmlEditLoader.load();

        VBox pane = new VBox();
        pane.setPrefSize(800,240);
        pane.getChildren().addAll(displayView, editView);

        FeaturePresenter.getInstance().featureProperty().set(FeatureParser.parseFeature(STANDARD_FEATURE));
        FeaturePresenter.getInstance().modeProperty().set(Mode.DISPLAY);

        return pane;
    }
}
