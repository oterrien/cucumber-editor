package com.ote.app.view.feature;

import com.ote.app.model.Feature;
import com.ote.app.model.FeatureFormatter;
import com.ote.app.model.FeatureParser;
import com.ote.app.view.AbstractPresenter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 23/12/2015.
 */
public class FeaturePresenter extends AbstractPresenter<Feature, IFeatureView> {

    public FeaturePresenter(IFeatureView view) {
        super(view);
    }

    @Override
    protected void fillView(Feature model) {
        this.getView().setTitle(model.getTitle());
        this.getView().setDescription(model.getDescription().getLine().
                stream().map(l -> l.getContent()).collect(Collectors.joining("\r\n")));
    }

    @Override
    protected void fillModel() {
        String featureString = FeatureFormatter.format(this.getView().getTitle(), this.getView().getDescription());
        this.setModel(FeatureParser.parseFeature(featureString));
    }
}
