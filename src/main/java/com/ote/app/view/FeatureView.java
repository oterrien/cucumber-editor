package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.model.Feature;
import com.ote.app.model.FeatureParser;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by Olivier on 19/12/2015.
 */
public class FeatureView implements IFeatureView {

    private static final String STANDARD_FEATURE =
            "Feature: Feature View Management\r\n" +
                    "\tIn order to test the feature view\r\n" +
                    "\tAs a user\r\n" +
                    "\tI want to create new feature and update data\r\n";

    @FXML
    private TextFlow displayPane;

    @FXML
    private Pane editPane;

    @FXML
    private TextField titleComponent;

    @FXML
    private TextArea descriptionComponent;

    private StringProperty title = new SimpleStringProperty();

    private StringProperty description = new SimpleStringProperty();

    private Collection<Consumer<Feature>> onSetFeatureHandler;
    private Collection<Runnable> onValidateEditionHandler, onCancelEditionHandler;

    private ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.DISPLAY);

    private FeaturePresenter presenter;

    @FXML
    public void initialize() {

        this.presenter = new FeaturePresenter(this);

        this.displayPane.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                switchToEditMode();
            }
        });

        this.displayPane.visibleProperty().bind(Bindings.when(this.mode.isEqualTo(Mode.DISPLAY)).then(true).otherwise(false));
        this.displayPane.visibleProperty().addListener((observable, oldValue, newValue) -> onModeChange(this.displayPane, 200, newValue));

        this.editPane.visibleProperty().bind(Bindings.when(this.mode.isEqualTo(Mode.DISPLAY)).then(false).otherwise(true));
        this.editPane.visibleProperty().addListener((observable, oldValue, newValue) -> onModeChange(this.editPane, 200, newValue));
        this.editPane.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.titleComponent.setText(this.title.get());
                this.descriptionComponent.setText(this.description.get());
            }
        });


        // TODO : put in editPane the content of Feature

        addOnValidateEditionHandler(this::switchToDisplayMode);
        addOnValidateEditionHandler(this::saveEdition);
        addOnCancelEditionHandler(this::switchToDisplayMode);

        // For TESTING
        this.setFeature(FeatureParser.parseFeature(STANDARD_FEATURE));
        initTextFlow();
    }

    private void initTextFlow() {
        // TODO : put in displayPane the content of Feature
        Text featureName = new Text("Feature: ");
        featureName.getStyleClass().add("featureName");
        this.displayPane.getChildren().add(featureName);
        Text featureTitle = new Text(this.getTitle());
        featureTitle.getStyleClass().add("featureTitle");
        featureTitle.textProperty().bind(this.titleComponent.textProperty());
        this.displayPane.getChildren().add(featureTitle);
        Text line = new Text("\r\n");
        this.displayPane.getChildren().add(line);
        Text description = new Text(this.getDescription());
        description.getStyleClass().add("description");
        description.textProperty().bind(this.descriptionComponent.textProperty());
        this.displayPane.getChildren().add(description);
    }

    @FXML
    @Override
    public void validate() {
        if (this.onValidateEditionHandler != null) {
            this.onValidateEditionHandler.stream().forEach(h -> h.run());
        }
    }

    @FXML
    @Override
    public void cancel() {
        if (this.onCancelEditionHandler != null) {
            this.onCancelEditionHandler.stream().forEach(h -> h.run());
        }
    }

    private void switchToDisplayMode() {
        this.mode.set(Mode.DISPLAY);
    }

    private void switchToEditMode() {
        this.mode.set(Mode.EDIT);
    }

    @Override
    public Mode getMode() {
        return this.mode.get();
    }

    @Override
    public void setMode(Mode mode) {
        this.mode.set(mode);
    }

    public void saveEdition() {
        this.setTitle(this.titleComponent.getText());
        this.setDescription(this.descriptionComponent.getText());
    }

    private void onModeChange(Pane pane, int normalHeight, boolean newValue) {
        if (newValue) {
            VBox.setVgrow(pane, Priority.ALWAYS);
            pane.setPrefHeight(normalHeight);
        } else {
            VBox.setVgrow(pane, Priority.NEVER);
            pane.setPrefHeight(0);
        }

        pane.setMinHeight(Region.USE_PREF_SIZE);
        pane.setMaxHeight(Region.USE_PREF_SIZE);
    }

    @Override
    public String getTitle() {
        return this.title.get();
    }

    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public String getDescription() {
        return this.description.get();
    }

    @Override
    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public void setFeature(Feature feature) {
        if (onSetFeatureHandler != null) {
            this.onSetFeatureHandler.stream().forEach(h -> h.accept(feature));
        }
    }

    @Override
    public void addOnSetFeatureHandler(Consumer<Feature> handler) {

        if (this.onSetFeatureHandler == null)
            this.onSetFeatureHandler = new ArrayList<>(5);

        this.onSetFeatureHandler.add(handler);
    }

    @Override
    public void addOnValidateEditionHandler(Runnable handler) {

        if (this.onValidateEditionHandler == null)
            this.onValidateEditionHandler = new ArrayList<>(5);

        this.onValidateEditionHandler.add(handler);
    }

    @Override
    public void addOnCancelEditionHandler(Runnable handler) {

        if (this.onCancelEditionHandler == null)
            this.onCancelEditionHandler = new ArrayList<>(5);

        this.onCancelEditionHandler.add(handler);
    }
}
