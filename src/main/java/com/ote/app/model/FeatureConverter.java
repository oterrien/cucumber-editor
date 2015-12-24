package com.ote.app.model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 24/12/2015.
 */
public final class FeatureConverter implements IModelConverter<Feature> {

    private static final FeatureConverter INSTANCE = new FeatureConverter();

    private IParser<Feature> parser;
    private IFormatter<Feature> formatter;

    private FeatureConverter() {

        parser = new Parser();
        formatter = new Formatter();
    }

    public static FeatureConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public IParser<Feature> getParser() {
        return this.parser;
    }

    @Override
    public IFormatter<Feature> getFormatter() {
        return this.formatter;
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
}
