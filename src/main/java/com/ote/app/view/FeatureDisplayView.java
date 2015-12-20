package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;
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
 * Created by Olivier on 19/12/2015.
 */
public class FeatureDisplayView implements IFeatureView {

    @FXML
    private TextFlow root;

    private Collection<Text> title = new ArrayList<>(2);

    private Collection<Text> description = new ArrayList<>(10);

    private MultiConsumer<Void> showCommand = new MultiConsumer<>();

    private MultiConsumer<Void> hideCommand = new MultiConsumer<>();

    @FXML
    public void initialize() {

        FeaturePresenter.getInstance().register(this);

        showCommand.addHandler(none -> {
            this.root.getChildren().clear();
            this.title.stream().forEach(text -> this.root.getChildren().add(text));
            this.description.stream().forEach(text -> this.root.getChildren().add(text));
        });

        showCommand.addHandler(none -> {
            VBox.setVgrow(root, Priority.ALWAYS);
            root.setPrefHeight(200);

            root.setMinHeight(Region.USE_PREF_SIZE);
            root.setMaxHeight(Region.USE_PREF_SIZE);

            root.setVisible(true);
        });

        hideCommand.addHandler(none -> {
            VBox.setVgrow(root, Priority.NEVER);
            root.setPrefHeight(0);

            root.setMinHeight(Region.USE_PREF_SIZE);
            root.setMaxHeight(Region.USE_PREF_SIZE);

            root.setVisible(false);
        });

        FeaturePresenter.getInstance().modeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Mode.DISPLAY) {
                show();
            } else {
                hide();
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
    public Collection<String> getDescription() {
        return this.description.stream().map(text -> text.getText()).collect(Collectors.toList());
    }

    @Override
    public void setDescription(Collection<String> description) {

        this.description.clear();

        description.forEach(line -> {
            Text newLine = new Text("\r\n");
            newLine.getStyleClass().add("description");
            this.description.add(newLine);
            Text featureDescription = new Text(line);
            featureDescription.getStyleClass().add("description");
            this.description.add(featureDescription);
        });
    }

    @Override
    public Mode getMode() {
        return Mode.DISPLAY;
    }

    @Override
    public MultiConsumer<Void> getShowCommand() {
        return this.showCommand;
    }

    @Override
    public MultiConsumer<Void> getHideCommand() {
        return this.hideCommand;
    }

    @Override
    public void show() {
        this.showCommand.accept(null);
    }

    @Override
    public void hide() {
        this.hideCommand.accept(null);
    }
}
