package com.ote.app.model;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Olivier on 24/12/2015.
 */
public final class FeatureDescriptionConverter implements IModelConverter<Description> {

    private static final FeatureDescriptionConverter INSTANCE = new FeatureDescriptionConverter();

    private IParser<Description> parser;
    private IFormatter<Description> formatter;

    private FeatureDescriptionConverter() {

        parser = new Parser();
        formatter = new Formatter();
    }

    public static FeatureDescriptionConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public IParser<Description> getParser() {
        return this.parser;
    }

    @Override
    public IFormatter<Description> getFormatter() {
        return this.formatter;
    }

    private static class Parser implements IParser<Description> {

        @Override
        public Description parse(String... text) {

            Description description = new Description();

            IntStream.range(0, text.length).
                    forEach(i -> {
                        Line line = new Line();
                        line.setContent(text[i].replaceAll("(\t)", "").trim());
                        if (line.getContent().startsWith("#")) {
                            line.setIsCommented(true);
                        }
                        description.getLine().add(line);
                    });

            return description;
        }
    }

    private static class Formatter implements IFormatter<Description> {

        @Override
        public String format(Description model) {

            return model.getLine().stream().
                    map(l -> l.getContent()).collect(Collectors.joining("\r\n"));
        }
    }
}
