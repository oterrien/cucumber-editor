package com.ote.app.view.feature;

import com.ote.app.model.Feature;
import com.ote.app.view.AbstractView;

/**
 * Created by Olivier on 17/12/2015.
 */
public class FeatureViewMock extends AbstractView<FeaturePresenter, Feature> implements IFeatureView {

    private String title;

    private String description;

    public FeatureViewMock() {
        this.initialize();
    }

    @Override
    protected FeaturePresenter createPresenter() {
        return new FeaturePresenter(this);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}