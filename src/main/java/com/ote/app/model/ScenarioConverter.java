package com.ote.app.model;

import javafx.scene.Node;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 24/12/2015.
 */
public final class ScenarioConverter extends AbstractConverter<ScenarioType> implements IModelConverter<ScenarioType> {

    private static final ScenarioConverter INSTANCE = new ScenarioConverter();

    private ScenarioConverter() {
        super(new Parser(), new Formatter(), new DisplayFormatter());
    }

    public static ScenarioConverter getInstance() {
        return INSTANCE;
    }

    private static class Parser implements IParser<ScenarioType> {

        @Override
        public ScenarioType parse(String... text) {

            Scenario scenario = new Scenario();
            scenario.setTitle(text[0].replaceAll("Scenario:", "").trim());
            scenario.setSteps(ScenarioStepsConverter.getInstance().getParser().
                    parse(Arrays.asList(text).subList(1, text.length).stream().collect(Collectors.joining("\r\n"))));
            return scenario;
        }
    }

    private static class Formatter implements IFormatter<ScenarioType> {

        @Override
        public String format(ScenarioType model, boolean isIndented) {

            StringBuilder sb = new StringBuilder("Scenario: ").
                    append(model.getTitle()).
                    append("\r\n").
                    append(ScenarioStepsConverter.getInstance().getFormatter().
                            format(model.getSteps(), isIndented));
            return sb.toString();
        }
    }

    private static class DisplayFormatter implements IDisplayFormatter<ScenarioType> {

        @Override
        public Collection<Node> format(ScenarioType model) {

            Collection<Node> list = new ArrayList<>(10);

            Text name = new Text("Scenario: ");
            name.getStyleClass().add("name");
            list.add(name);

            Text title = new Text(model.getTitle());
            title.getStyleClass().add("title");
            list.add(title);

            list.addAll(ScenarioStepsConverter.getInstance().getDisplayFormatter().format(model.getSteps()));

            return list;
        }
    }
}
