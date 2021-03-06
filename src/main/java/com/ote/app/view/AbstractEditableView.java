package com.ote.app.view;

import com.ote.app.Mode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Created by Olivier on 24/12/2015.
 */
public abstract class AbstractEditableView<P extends IPresenter<M, ? extends IView<M>>, M> extends AbstractView<P, M> implements IView<M> {

    private ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.INIT);

    private Pane displayPane, editPane;
    private SimpleDoubleProperty prefHeight = new SimpleDoubleProperty();

    protected void initialize(Pane displayPane, Pane editPane) {

        super.initialize();

        this.prefHeight.set(displayPane.getPrefHeight());

        this.displayPane = displayPane;
        this.initialize(displayPane);

        this.editPane = editPane;
        this.initialize(editPane);

        // On validation or cancellation, change back the mode to DISPLAY
        this.getValidateCommand().addHandler(aVoid -> this.setMode(Mode.DISPLAY));
        this.getCancelCommand().addHandler(aVoid -> this.setMode(Mode.DISPLAY));

        // Listen mode change
        this.modeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Mode.DISPLAY)) {
                display();
            } else {
                edit();
            }
        });
    }

    private void initialize(Pane pane) {

        if (pane.getParent() instanceof VBox) {
            VBox.setVgrow(pane, Priority.NEVER);
        }
        
        pane.setPrefHeight(this.prefHeight.doubleValue());
        pane.setMinHeight(Region.USE_PREF_SIZE);
        pane.setMaxHeight(Region.USE_PREF_SIZE);

        this.prefHeight.addListener((observable, oldValue, newValue) -> {
            pane.setPrefHeight(newValue.doubleValue());
        });
    }

    public SimpleDoubleProperty prefHeightProperty() {
        return prefHeight;
    }

    protected void validateOnShitEnter(KeyEvent event) {
        if (event.isShiftDown() && event.getCode() == KeyCode.ENTER) {
            validate();
        }
    }

    protected Mode getMode() {
        return mode.get();
    }

    protected ObjectProperty<Mode> modeProperty() {
        return mode;
    }

    protected void setMode(Mode mode) {
        this.mode.set(mode);
    }

    protected void display() {
        fillDisplayPane();
        show(this.displayPane);
        hide(this.editPane);
    }

    protected void edit() {
        show(this.editPane);
        hide(this.displayPane);
    }

    protected void hide(Pane pane) {
        pane.setPrefHeight(0);
        pane.setVisible(false);
    }

    protected void show(Pane pane) {
        pane.setPrefHeight(this.prefHeight.get());
        pane.setVisible(true);
    }

    protected abstract void fillDisplayPane();
}
