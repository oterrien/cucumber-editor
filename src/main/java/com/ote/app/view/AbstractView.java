package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Created by Olivier on 21/12/2015.
 */
public abstract class AbstractView<T extends Pane> implements IView {

    @FXML
    protected T root;

    private MultiConsumer<Void> showCommand = new MultiConsumer<>();

    private MultiConsumer<Void> hideCommand = new MultiConsumer<>();

    @Override
    public void initialize() {

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

    @Override
    public void show() {
        this.showCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getShowCommand() {
        return this.showCommand;
    }

    @Override
    public void hide() {
        this.hideCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getHideCommand() {
        return this.hideCommand;
    }

    @Override
    public Mode getMode() {
        return Mode.DISPLAY;
    }
}
