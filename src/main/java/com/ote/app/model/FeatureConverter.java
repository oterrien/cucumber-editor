package com.ote.app.model;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Olivier on 24/12/2015.
 */
public final class FeatureConverter extends AbstractConverter<Feature> implements IModelConverter<Feature> {

    private static final FeatureConverter INSTANCE = new FeatureConverter();

    private FeatureConverter() {
        super(new Parser(), new Formatter(), new DisplayFormatter());
    }

    public static FeatureConverter getInstance() {
        return INSTANCE;
    }

    private static class Parser implements IParser<Feature> {

        @Override
        public Feature parse(String... text) {

            Feature feature = new Feature();
            feature.setTitle(text[0].replaceAll("Feature:", "").trim());
            feature.setDescription(FeatureDescriptionConverter.getInstance().
                    getParser().parse(Arrays.copyOfRange(text, 1, text.length)));

            return feature;
        }
    }

    private static class Formatter implements IFormatter<Feature> {

        @Override
        public String format(Feature model) {

            StringBuilder sb = new StringBuilder("Feature: ").
                    append(model.getTitle()).
                    append("\r\n").
                    append(FeatureDescriptionConverter.getInstance().
                            getFormatter().format(model.getDescription()));
            return sb.toString();
        }
    }

    private static class DisplayFormatter implements IDisplayFormatter<Feature> {

        @Override
        public Collection<Node> format(Feature model) {

            Collection<Node> list = new ArrayList<>(10);

            Text name = new Text("Feature: ");
            name.getStyleClass().add("name");
            list .add(name);

            Text title = new Text(model.getTitle());
            title.getStyleClass().add("title");
            list .add(title);

            list.addAll(FeatureDescriptionConverter.getInstance().getDisplayFormatter().format(model.getDescription()));

            return list;
        }
    }
}
