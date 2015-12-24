package com.ote.app.view;

import com.ote.app.command.MultiConsumer;

/**
 * Created by Olivier on 24/12/2015.
 */
public interface IView<M> {

    M getModel();

    void setModel(M param);

    default void validate() {
        getValidateCommand().accept(null);
    }

    MultiConsumer<Void> getValidateCommand();

    default void cancel() {
        getCancelCommand().accept(null);
    }

    MultiConsumer<Void> getCancelCommand();
}
