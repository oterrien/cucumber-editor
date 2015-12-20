package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.model.Feature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by Olivier on 17/12/2015.
 */
public class FeatureViewMock implements IFeatureView {

    private String title, description;

    private Collection<Consumer<Feature>> onSetFeatureHandler = new ArrayList<>(5);
    private Collection<Runnable> onValidateEditionHandler = new ArrayList<>(5);
    private Collection<Runnable> onCancelEditionHandler = new ArrayList<>(5);

    private Mode mode;

    private FeaturePresenter presenter;

    public FeatureViewMock() {
        this.presenter = new FeaturePresenter(this);
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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setFeature(Feature feature) {
        if (onSetFeatureHandler != null) {
            onSetFeatureHandler.stream().forEach(h -> h.accept(feature));
        }
    }

    @Override
    public void addOnSetFeatureHandler(Consumer<Feature> handler) {
        this.onSetFeatureHandler.add(handler);
    }

    @Override
    public void addOnValidateEditionHandler(Runnable handler) {
        this.onValidateEditionHandler.add(handler);
    }

    @Override
    public void addOnCancelEditionHandler(Runnable handler) {
        this.onCancelEditionHandler.add(handler);
    }

    @Override
    public Mode getMode() {
        return this.mode;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void validate() {

    }

    @Override
    public void cancel() {

    }
}
