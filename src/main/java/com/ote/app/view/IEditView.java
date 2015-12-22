package com.ote.app.view;

import com.ote.app.command.MultiConsumer;

/**
 * Created by Olivier on 21/12/2015.
 */
public interface IEditView extends IView {

    void validate();

    MultiConsumer<Void> getValidateCommand();

    void cancel();

    MultiConsumer<Void> getCancelCommand();
}
