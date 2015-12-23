package com.ote.app.view.feature3;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;
import com.ote.app.model.Description;
import com.ote.app.model.Feature;
import com.ote.app.model.FeatureParser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 22/12/2015.
 */
public class FeatureView implements IFeatureView {

    @FXML
    private TextFlow displayPane;

    @FXML
    private VBox editPane;

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    private MultiConsumer<Void> validateCommand = new MultiConsumer<>();

    private MultiConsumer<Void> cancelCommand = new MultiConsumer<>();

    private FeaturePresenter presenter;

    private ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.INIT);

    @FXML
    public void initialize() {

        this.presenter = new FeaturePresenter(this);

        this.mode.addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Mode.DISPLAY)) {
                displayFeature();
                show(displayPane);
                hide(editPane);
            } else {
                hide(displayPane);
                show(editPane);
            }
        });

        this.validateCommand.addHandler(aVoid -> this.mode.set(Mode.DISPLAY));

        this.cancelCommand.addHandler(aVoid1 -> {
            this.setTitle(this.getFeature().getTitle());
            this.setDescription(this.getFeature().getDescription().getLine().
                    stream().map(l -> l.getContent()).collect(Collectors.joining("\r\n")));
        });

        this.cancelCommand.addHandler(aVoid -> this.mode.set(Mode.DISPLAY));

        this.loadFeature();

        this.mode.set(Mode.DISPLAY);
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
    @Override
    public void validate() {
        validateCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getValidateCommand() {
        return this.validateCommand;
    }

    @FXML
    @Override
    public void cancel() {
        cancelCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getCancelCommand() {
        return this.cancelCommand;
    }

    @Override
    public Feature getFeature() {
        return this.presenter.getFeature();
    }

    @Override
    public void setFeature(Feature feature) {
        this.presenter.setFeature(feature);
    }

    @FXML
    public void edit(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            this.mode.set(Mode.EDIT);
        }
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

    private void displayFeature() {

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

    public void loadFeature() {

        this.setFeature(FeatureParser.parseFeature(STANDARD_FEATURE));

    }
}
