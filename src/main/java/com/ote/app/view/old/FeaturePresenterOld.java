package com.ote.app.view.old;

import com.ote.app.Mode;
import com.ote.app.model.Feature;
import com.ote.app.view.IFeatureView;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 17/12/2015.
 */
public class FeaturePresenterOld {

    private IFeatureViewOld view;
    private Feature feature;

    public FeaturePresenterOld(IFeatureViewOld view) {
        this.view = view;
        init();
    }

    private void init() {

        this.view.addOnSetFeatureHandler(this::loadFeatureInView);
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    private void loadFeatureInView(Feature feature) {

        this.feature = feature;

        this.view.setTitle(this.feature.getTitle());
        this.view.setDescription(this.feature.getDescription().getLine().
                stream().map(l -> l.getContent()).collect(Collectors.joining("\r\n")));

        this.view.setMode(Mode.DISPLAY);
    }
}
