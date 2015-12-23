package com.ote.app.view.feature3;

import com.ote.app.command.MultiConsumer;
import com.ote.app.model.Feature;

/**
 * Created by Olivier on 23/12/2015.
 */
public interface IFeatureView {

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    default void validate(){
        getValidateCommand().accept(null);
    }

    MultiConsumer<Void> getValidateCommand();

    default void cancel(){
        getCancelCommand().accept(null);
    }

    MultiConsumer<Void> getCancelCommand();

    Feature getFeature();

    void setFeature(Feature feature);

}
