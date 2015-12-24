package com.ote.app.model;

import java.util.stream.IntStream;

/**
 * Created by Olivier on 24/12/2015.
 */
public final class ScenarioStepsConverter implements IModelConverter<Steps> {

    private static final ScenarioStepsConverter INSTANCE = new ScenarioStepsConverter();

    private IParser<Steps> parser;
    private IFormatter<Steps> formatter;

    private ScenarioStepsConverter() {

        parser = new Parser();
        formatter = new Formatter();
    }

    public static ScenarioStepsConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public IParser<Steps> getParser() {
        return this.parser;
    }

    @Override
    public IFormatter<Steps> getFormatter() {
        return this.formatter;
    }

    private static class Parser implements IParser<Steps> {

        @Override
        public Steps parse(String... text) {

            return null;
        }
    }

    private static class Formatter implements IFormatter<Steps> {

        @Override
        public String format(Steps model) {
            return null;
        }
    }
}
