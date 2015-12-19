package com.ote.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olivier on 17/12/2015.
 */
public class FeatureViewMock implements IFeatureView {

    private String title;
    private List<String> description = new ArrayList<String>(10);

    private Runnable onLoadFeature;

    private FeaturePresenter presenter;

    public FeatureViewMock(String feature) {
        this.presenter = new FeaturePresenter(this, feature);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<String> getDescription() {
        return description;
    }

    @Override
    public Runnable getOnLoadFeature() {
        return onLoadFeature;
    }

    @Override
    public void setOnLoadFeature(Runnable handler) {
        this.onLoadFeature = handler;
    }
}
