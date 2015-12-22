package com.ote.app.view.feature2.edit;

import com.ote.app.command.MultiConsumer;
import com.ote.app.view.feature2.display.IFeatureDisplayView;

/**
 * Created by Olivier on 20/12/2015.
 */
public interface IFeatureEditView extends IFeatureDisplayView {

    void validate();

    MultiConsumer<Void> getValidateCommand();

    void cancel();

    MultiConsumer<Void> getCancelCommand();
}
