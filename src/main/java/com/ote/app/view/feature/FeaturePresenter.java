package com.ote.app.view.feature;

import com.ote.app.model.Feature;
import com.ote.app.model.FeatureConverter;
import com.ote.app.model.FeatureDescriptionConverter;
import com.ote.app.view.AbstractPresenter;
import com.ote.app.view.IPresenter;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 23/12/2015.
 */
public class FeaturePresenter extends AbstractPresenter<Feature, IFeatureView> implements IPresenter<Feature, IFeatureView> {

    public FeaturePresenter(IFeatureView view) {
        super(view);
    }

    @Override
    protected void fillView(Feature model) {
        this.getView().setTitle(model.getTitle());
        this.getView().setDescription(FeatureDescriptionConverter.getInstance().
                getFormatter().format(model.getDescription()));
    }

    @Override
    protected void fillModel() {
        this.setModel(FeatureConverter.getInstance().getParser().
                parse(this.getView().getTitle(), this.getView().getDescription()));
    }
}
