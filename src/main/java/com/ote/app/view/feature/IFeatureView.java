package com.ote.app.view.feature;

import com.ote.app.view.IView;

import java.util.Collection;

/**
 * Created by Olivier on 17/12/2015.
 */
public interface IFeatureView extends IView {

    String getTitle();

    void setTitle(String title);

    Collection<String> getDescription();

    void setDescription(Collection<String> description);
}
