package com.ote.app.view.feature3;

import com.ote.app.model.Feature;
import com.ote.app.model.FeatureParser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 23/12/2015.
 */
public class FeaturePresenter {

    private IFeatureView view;

    private ObjectProperty<Feature> feature = new SimpleObjectProperty<>();

    public FeaturePresenter(IFeatureView view) {
        this.view = view;
        initialize();
    }

    public Feature getFeature() {
        return feature.get();
    }

    public void setFeature(Feature feature) {
        this.feature.set(feature);
    }

    private void initialize() {

        // On feature change, set view's title and view's description
        this.feature.addListener((observable, oldValue, newValue) -> {
            this.view.setTitle(newValue.getTitle());
            this.view.setDescription(newValue.getDescription().getLine().
                    stream().map(l -> l.getContent()).collect(Collectors.joining("\r\n")));
        });

        // On view's validation, change feature
        this.view.getValidateCommand().addHandler(aVoid -> {

            StringBuilder sb = new StringBuilder("Feature: ").
                    append(this.view.getTitle()).
                    append("\r\n").
                    append(this.view.getDescription());

            this.feature.set(FeatureParser.parseFeature(sb.toString()));
        });
    }

}
