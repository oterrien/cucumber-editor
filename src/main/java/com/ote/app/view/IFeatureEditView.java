package com.ote.app.view;

import com.ote.app.command.MultiConsumer;

/**
 * Created by Olivier on 20/12/2015.
 */
public interface IFeatureEditView extends IFeatureView {

    void validate();

    MultiConsumer<Void> getValidateCommand();

    void cancel();

    MultiConsumer<Void> getCancelCommand();
}
