package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

/**
 * Created by Olivier on 21/12/2015.
 */
public abstract class AbstractEditView<T extends Pane> extends AbstractView<T> implements IEditView {

    private MultiConsumer<Void> validateCommand = new MultiConsumer<>();

    private MultiConsumer<Void> cancelCommand = new MultiConsumer<>();

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
    public Mode getMode() {
        return Mode.EDIT;
    }
}
