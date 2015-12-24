package com.ote.app.view.feature;

import com.ote.app.Mode;
import com.ote.app.model.Description;
import com.ote.app.model.Feature;
import com.ote.app.model.FeatureParser;
import com.ote.app.view.AbstractEditableView;
import com.ote.app.view.AbstractView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by Olivier on 22/12/2015.
 */
public class FeatureView extends AbstractEditableView<FeaturePresenter, Feature> implements IFeatureView {

    @FXML
    private TextFlow displayPane;

    @FXML
    private VBox editPane;

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    @FXML
    public void initialize() {

        super.initialize();

        // SHIFT + ENTER -> validate description and title
        EventHandler<KeyEvent> validateOnShiftEnter = (event) -> {
            if (event.isShiftDown() && event.getCode() == KeyCode.ENTER) {
                validate();
            }
        };

        this.description.setOnKeyPressed(validateOnShiftEnter);
        this.title.setOnKeyPressed(validateOnShiftEnter);

        this.load();
        this.setMode(Mode.DISPLAY);
    }

    @Override
    protected FeaturePresenter createPresenter() {
        return new FeaturePresenter(this);
    }

    @Override
    public String getTitle() {
        return this.title.getText();
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public String getDescription() {
        return this.description.getText();
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @FXML
    public void onValidateClicked() {
        validate();
    }

    @FXML
    public void onCancelClicked() {
        cancel();
    }

    @FXML
    public void onDisplayPaneClicked(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            this.setMode(Mode.EDIT);
        }
    }

    @Override
    protected void display() {
        fillDisplayPane();
        hide(this.editPane);
        show(this.displayPane);
    }

    @Override
    protected void edit() {
        hide(this.displayPane);
        show(this.editPane);
    }

    private void hide(Pane pane) {

        VBox.setVgrow(pane, Priority.NEVER);
        pane.setPrefHeight(0);
        pane.setMinHeight(Region.USE_PREF_SIZE);
        pane.setMaxHeight(Region.USE_PREF_SIZE);
        pane.setVisible(false);
    }

    private void show(Pane pane) {

        VBox.setVgrow(pane, Priority.ALWAYS);
        pane.setPrefHeight(200);
        pane.setMinHeight(Region.USE_PREF_SIZE);
        pane.setMaxHeight(Region.USE_PREF_SIZE);
        pane.setVisible(true);
    }

    private void fillDisplayPane() {

        this.displayPane.getChildren().clear();

        Text featureName = new Text("Feature: ");
        featureName.getStyleClass().add("name");
        this.displayPane.getChildren().add(featureName);

        Text featureTitle = new Text(this.getTitle());
        featureTitle.getStyleClass().add("title");
        this.displayPane.getChildren().add(featureTitle);

        Description description = FeatureParser.parseDescription(this.getDescription());
        description.getLine().stream().map(l -> l.getContent()).forEach(s -> {
            this.displayPane.getChildren().add(new Text("\r\n"));
            Text featureDescription = new Text(s);
            featureDescription.getStyleClass().add(s.startsWith("#") ? "comment" : "description");
            this.displayPane.getChildren().add(featureDescription);
        });
    }

    private static final String STANDARD_FEATURE =
            "Feature: Feature View Management\r\n" +
                    "\tIn order to test the feature view\r\n" +
                    "\tAs a user\r\n" +
                    "\tI want to create new feature and update data\r\n";

    public void load() {

        this.setModel(FeatureParser.parseFeature(STANDARD_FEATURE));

    }
}
