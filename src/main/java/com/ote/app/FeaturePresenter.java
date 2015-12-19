package com.ote.app;

import com.ote.app.model.Feature;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 17/12/2015.
 */
public class FeaturePresenter {

    private IFeatureView view;
    private Feature feature;

    public FeaturePresenter(IFeatureView view, String feature) {
        this.view = view;
        this.feature = FeatureParser.parseFeature(feature);
        init();
    }

    private void init() {
        this.view.setOnLoadFeature(this::loadFeatureInView);
    }

    private void loadFeatureInView() {
        this.view.setTitle(this.feature.getTitle());
        /*this.view.getDescription().addAll(this.feature.getDescription().getLine().
                stream().map(l -> l.getContent()).collect(Collectors.toList()));*/
    }
}
