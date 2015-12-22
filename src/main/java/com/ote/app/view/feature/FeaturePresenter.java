package com.ote.app.view.feature;

import com.ote.app.model.Feature;
import com.ote.app.model.FeatureParser;
import com.ote.app.view.AbstractPresenter;
import com.sun.istack.internal.NotNull;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 17/12/2015.
 */
public final class FeaturePresenter extends AbstractPresenter {

    private FeaturePresenter() {

    }

    private static final FeaturePresenter INSTANCE = new FeaturePresenter();

    public static FeaturePresenter getInstance() {
        return INSTANCE;
    }

    private ObjectProperty<Feature> feature = new SimpleObjectProperty<>();

    public ObjectProperty<Feature> featureProperty() {
        return this.feature;
    }

    public void register(IFeatureView view) {

        super.register(view);

        this.feature.addListener((observable, oldValue, newValue) -> this.updateView(view, newValue));
    }

    private void updateView(IFeatureView view, @NotNull Feature feature) {

        view.setTitle(feature.getTitle());
        view.setDescription(feature.getDescription().getLine().
                stream().map(line -> line.getContent()).collect(Collectors.toList()));
    }

    public void register(IFeatureEditView view) {

        this.register((IFeatureView) view);

        // On validate, change the feature with view data
        view.getValidateCommand().addHandler(p -> {
            StringBuilder sb = new StringBuilder("Feature: ").
                    append(view.getTitle()).
                    append("\r\n").
                    append(view.getDescription().stream().collect(Collectors.joining("\r\n")));

            this.feature.set(FeatureParser.parseFeature(sb.toString()));
        });

        super.register(view);
    }
}
