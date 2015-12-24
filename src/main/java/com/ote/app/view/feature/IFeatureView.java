package com.ote.app.view.feature;

import com.ote.app.model.Feature;
import com.ote.app.view.IView;

/**
 * Created by Olivier on 23/12/2015.
 */
public interface IFeatureView extends IView<Feature> {

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);
}
