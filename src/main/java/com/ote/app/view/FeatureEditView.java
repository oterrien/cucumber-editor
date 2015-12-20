package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 19/12/2015.
 */
public class FeatureEditView implements IFeatureEditView {

    @FXML
    private VBox root;

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    private MultiConsumer<Void> showCommand = new MultiConsumer<>();
    private MultiConsumer<Void> hideCommand = new MultiConsumer<>();
    private MultiConsumer<Void> validateCommand = new MultiConsumer<>();
    private MultiConsumer<Void> cancelCommand = new MultiConsumer<>();

    @FXML
    public void initialize() {

        FeaturePresenter.getInstance().register(this);

        description.setOnKeyPressed(event -> {
            if(event.isShiftDown() && event.getCode()== KeyCode.ENTER){
                validate();
            }
        });

        validateCommand.addHandler(none -> FeaturePresenter.getInstance().modeProperty().set(Mode.DISPLAY));

        cancelCommand.addHandler(none -> FeaturePresenter.getInstance().modeProperty().set(Mode.DISPLAY));

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
                hide();
            } else {
                show();
            }
        });
    }

    @FXML
    @Override
    public void validate() {
        this.validateCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getValidateCommand() {
        return this.validateCommand;
    }

    @FXML
    @Override
    public void cancel() {
        this.cancelCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getCancelCommand() {
        return this.cancelCommand;
    }

    @Override
    public Mode getMode() {
        return Mode.EDIT;
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
    public Collection<String> getDescription() {
        return Arrays.asList(this.description.getText().split("(\r\n|\n\r|\r|\n)"));
    }

    @Override
    public void setDescription(Collection<String> description) {
        this.description.setText(description.stream().collect(Collectors.joining("\r\n")));
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
