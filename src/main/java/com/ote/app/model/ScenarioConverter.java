package com.ote.app.model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 24/12/2015.
 */
public class ScenarioConverter implements IModelConverter<Scenario> {

    private static final ScenarioConverter INSTANCE = new ScenarioConverter();

    private IParser<Scenario> parser;
    private IFormatter<Scenario> formatter;

    private ScenarioConverter() {

        this.parser = new Parser();
        this.formatter = new Formatter();
    }

    public static ScenarioConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public IParser<Scenario> getParser() {
        return parser;
    }

    @Override
    public IFormatter<Scenario> getFormatter() {
        return formatter;
    }

    private static class Parser implements IParser<Scenario> {

        @Override
        public Scenario parse(String... text) {

            Scenario scenario = new Scenario();
            scenario.setTitle(text[0].replaceAll("Scenario:", "").trim());
            scenario.setSteps(ScenarioStepsConverter.getInstance().getParser().
                    parse(Arrays.copyOfRange(text, 1, text.length)));

            return scenario;
        }
    }

    private static class Formatter implements IFormatter<Scenario> {

        @Override
        public String format(Scenario model) {

            StringBuilder sb = new StringBuilder("Feature: ").
                    append(model.getTitle()).
                    append("\r\n").
                    append(ScenarioStepsConverter.getInstance().getFormatter().
                            format(model.getSteps()));

            return sb.toString();
        }

    }
}
