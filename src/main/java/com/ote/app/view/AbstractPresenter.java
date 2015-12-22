package com.ote.app.view;

import com.ote.app.Mode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by Olivier on 21/12/2015.
 */
public abstract class AbstractPresenter {

    private ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.INIT);

    public ObjectProperty<Mode> modeProperty() {
        return this.mode;
    }

    protected void register(IView view) {

        // When mode is changed, show or hide view regarding to the view's mode
        this.mode.addListener((observable, oldValue, newValue) -> {
            if (newValue == view.getMode()) {
                view.show();
            } else {
                view.hide();
            }
        });
    }

    protected void register(IEditView view) {

        // Validate or cancel Edition should switch back to DISPLAY mode
        view.getValidateCommand().addHandler(p -> this.mode.set(Mode.DISPLAY));
        view.getCancelCommand().addHandler(p -> this.mode.set(Mode.DISPLAY));
    }
}
