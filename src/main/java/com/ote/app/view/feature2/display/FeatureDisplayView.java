package com.ote.app.view.feature2.display;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;
import com.ote.app.model.Description;
import com.ote.app.model.FeatureParser;
import com.ote.app.view.feature.FeaturePresenter;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 22/12/2015.
 */
public class FeatureDisplayView implements IFeatureDisplayView {

    @FXML
    protected TextFlow root;

    private Collection<Text> title = new ArrayList<>(2);

    private Collection<Text> description = new ArrayList<>(10);

    private MultiConsumer<Void> showCommand = new MultiConsumer<>();

    private MultiConsumer<Void> hideCommand = new MultiConsumer<>();

    private FeatureDisplayPresenter presenter;

    @FXML
    @Override
    public void initialize() {

        /**
         * TODO CF FeatureView / FeaturePresenter Plus haut niveau, qui pilote quelle vue (display ou edit) afficher
         */

        this.presenter = new FeatureDisplayPresenter(this);

        showCommand.addHandler(none -> {
            if (root != null) {

                if (root.getParent() instanceof VBox) {
                    VBox.setVgrow(root, Priority.ALWAYS);
                }

                root.setPrefHeight(200);
                root.setMinHeight(Region.USE_PREF_SIZE);
                root.setMaxHeight(Region.USE_PREF_SIZE);
                root.setVisible(true);
            }
        });

        hideCommand.addHandler(none -> {
            if (root != null) {

                if (root.getParent() instanceof VBox) {
                    VBox.setVgrow(root, Priority.NEVER);
                }

                root.setPrefHeight(0);
                root.setMinHeight(Region.USE_PREF_SIZE);
                root.setMaxHeight(Region.USE_PREF_SIZE);
                root.setVisible(false);
            }
        });
    }

    @FXML
    public void edit(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            FeaturePresenter.getInstance().modeProperty().set(Mode.EDIT);
        }
    }

    @Override
    public String getTitle() {
        return this.title.stream().map(text -> text.getText()).collect(Collectors.joining(" "));
    }

    @Override
    public void setTitle(String title) {

        this.title.clear();

        Text featureName = new Text("Feature: ");
        featureName.getStyleClass().add("name");
        this.title.add(featureName);

        Text featureTitle = new Text(title);
        featureTitle.getStyleClass().add("title");
        this.title.add(featureTitle);
    }

    @Override
    public String getDescription() {
        return this.description.stream().map(text -> text.getText()).collect(Collectors.joining("\r\n"));
    }

    @Override
    public void setDescription(String description) {

        this.description.clear();

        Description descr = FeatureParser.parseDescription(description);

        descr.getLine().stream().map(line -> line.getContent()).forEach(content -> {
            Text newLine = new Text("\r\n");
            newLine.getStyleClass().add("description");
            this.description.add(newLine);
            Text featureDescription = new Text(content);
            featureDescription.getStyleClass().add(content.startsWith("#") ? "comment" : "description");
            this.description.add(featureDescription);
        });
    }

    @Override
    public void show() {
        getShowCommand().accept(null);
    }

    @Override
    public MultiConsumer<Void> getShowCommand() {
        return showCommand;
    }

    @Override
    public void hide() {
        getHideCommand().accept(null);
    }

    @Override
    public MultiConsumer<Void> getHideCommand() {
        return hideCommand;
    }

    @Override
    public Mode getMode() {
        return Mode.DISPLAY;
    }
}
