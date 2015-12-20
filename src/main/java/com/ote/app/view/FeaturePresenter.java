package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.model.Feature;
import com.ote.app.model.FeatureParser;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 17/12/2015.
 */
public final class FeaturePresenter {

    private static final FeaturePresenter INSTANCE = new FeaturePresenter();

    private ObjectProperty<Feature> feature = new SimpleObjectProperty<>();

    private ObjectProperty<Mode> mode = new SimpleObjectProperty<>();

    private FeaturePresenter() {

    }

    public ObjectProperty<Mode> modeProperty() {
        return this.mode;
    }

    public ObjectProperty<Feature> featureProperty() {
        return this.feature;
    }

    public static FeaturePresenter getInstance() {
        return INSTANCE;
    }

    public void register(IFeatureView view) {

        // Plug view's title and description to feature updates
        feature.addListener((observable, oldValue, newValue) -> this.updateView(view, newValue));

        // When showCommand is executed, reset title & description with the content of feature
        view.getShowCommand().addHandler(none -> this.updateView(view, this.feature.get()));

        if (view instanceof IFeatureEditView) {
            ((IFeatureEditView) view).getValidateCommand().addHandler(none -> {
                StringBuilder sb = new StringBuilder("Feature: ").
                        append(view.getTitle()).
                        append("\r\n").
                        append(view.getDescription().stream().collect(Collectors.joining("\r\n")));

                this.feature.set(FeatureParser.parseFeature(sb.toString()));
            });
        }

        Platform.runLater(() -> this.mode.set(Mode.DISPLAY));
    }

    private void updateView(IFeatureView view, Feature feature) {
        view.setTitle(feature.getTitle());
        view.setDescription(feature.getDescription().getLine().
                stream().map(l -> l.getContent()).collect(Collectors.toList()));
    }
}
