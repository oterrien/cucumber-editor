package com.ote.app.view;

import com.ote.app.Mode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by Olivier on 24/12/2015.
 */
public abstract class AbstractEditableView<P extends IPresenter<M, ? extends IView<M>>, M> extends AbstractView<P, M> implements IView<M> {

    private ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.INIT);

    protected void initialize() {

        super.initialize();

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

    protected abstract void display();

    protected abstract void edit();


    protected Mode getMode() {
        return mode.get();
    }

    protected ObjectProperty<Mode> modeProperty() {
        return mode;
    }

    protected void setMode(Mode mode) {
        this.mode.set(mode);
    }


}
