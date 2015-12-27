package com.ote.app.model;

import javafx.scene.Node;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 24/12/2015.
 */
public final class ScenarioStepsConverter extends AbstractConverter<Steps> implements IModelConverter<Steps> {

    private static final ScenarioStepsConverter INSTANCE = new ScenarioStepsConverter();

    private ScenarioStepsConverter() {
        super(new Parser(), new Formatter(), new DisplayFormatter());
    }

    public static ScenarioStepsConverter getInstance() {
        return INSTANCE;
    }

    private static class Parser implements IParser<Steps> {

        @Override
        public Steps parse(String... text) {

            Steps steps = new Steps();

            for (int i = 0; i < text.length; i++) {
                String stepStr = text[i].trim();

                Pattern pattern = Pattern.compile("^([Gg]iven|[Ww]hen|[Tt]hen|[Aa]nd|[Bb]ut)(.*)$");
                Matcher matcher = pattern.matcher(stepStr);

                if (matcher.find()) {

                    Definition stepDef = new Definition();
                    Step step = new Step();
                    step.setType(StepType.fromValue(matcher.group(1).trim().toLowerCase()));
                    step.setContent(matcher.group(2).trim());
                    stepDef.setStep(step);
                    steps.getLineOrDefinition().add(stepDef);
                } else if (stepStr.startsWith("|")) {

                    Definition stepDef = (Definition) steps.getLineOrDefinition().get(steps.getLineOrDefinition().size() - 1);

                    List<String> table = new ArrayList<>(10);
                    table.add(stepStr);
                    while (i + 1 < text.length && text[i + 1].trim().startsWith("|")) {
                        stepStr = text[++i].trim();
                        table.add(stepStr);
                    }
                    stepDef.setTable(TableConverter.getInstance().getParser().parse(table.toArray(new String[0])));
                } else {
                    Line line = new Line();
                    line.setContent(text[i].replaceAll("(\t)", "").trim());
                    if (stepStr.startsWith("#")) {
                        line.setIsCommented(true);
                    }
                    steps.getLineOrDefinition().add(line);
                }
            }

            return steps;
        }
    }

    private static class Formatter implements IFormatter<Steps> {

        @Override
        public String format(Steps model, boolean isIndented) {

            StringBuilder sb = new StringBuilder();

            model.getLineOrDefinition().stream().forEach(lineOrStep -> {

                if (lineOrStep instanceof Line) {
                    sb.append(format((Line) lineOrStep, isIndented));
                } else {
                    sb.append(format((Definition) lineOrStep, isIndented));
                }
            });

            return sb.toString();
        }

        private String format(Line line, boolean isIndented) {

            StringBuilder sb = new StringBuilder();
            sb.append(line.isIsCommented() ? "# " : (isIndented ? "\t" : "")).append(line.getContent()).append("\r\n");
            return sb.toString();
        }

        private String format(Definition stepDef, boolean isIndented) {

            StringBuilder sb = new StringBuilder();
            sb.append(isIndented ? "\t" : "").
                    append(stepDef.getStep().getType().value().toLowerCase()).
                    append(" ").
                    append(stepDef.getStep().getContent()).append("\r\n");

            if (stepDef.getTable() != null) {
                sb.append(TableConverter.getInstance().getFormatter().format(stepDef
                        .getTable(), isIndented)).append("\r\n");
            }
            return sb.toString();
        }
    }

    private static class DisplayFormatter implements IDisplayFormatter<Steps> {

        @Override
        public Collection<Node> format(Steps model) {

            Collection<Node> list = new ArrayList<>(10);

            model.getLineOrDefinition().forEach(lineOfDefinition -> {

                list.add(new Text("\r\n"));

                if (lineOfDefinition instanceof Line) {
                    Line line = (Line) lineOfDefinition;
                    String content = line.getContent();
                    Text text = new Text(content);
                    text.getStyleClass().add(content.startsWith("#") ? "comment" : "description");
                    list.add(text);
                } else {
                    Definition stepDef = (Definition) lineOfDefinition;
                    Step step = stepDef.getStep();
                    Text stepName = new Text(step.getType().value().toLowerCase());
                    stepName.getStyleClass().add("step");
                    list.add(stepName);
                    list.add(new Text("\t"));
                    Text stepContent = new Text(step.getContent());
                    stepContent.getStyleClass().add("description");
                    list.add(stepContent);
                    if (stepDef.getTable() != null) {
                        list.add(new Text("\r\n"));
                        list.addAll(TableConverter.getInstance().getDisplayFormatter().format(stepDef.getTable()));
                    }
                }
            });

            return list;
        }

        private List<Text> formatDescription(String description){

            description = description.replaceAll("\"", "'");

            Pattern pattern = Pattern.compile("(?<QUOTE>'([^']+)')|(?<PARAMETER><([^<]+[^>])>)");
            Matcher matcher = pattern.matcher(description);

            List<String> quotes = new ArrayList<>(10);
            List<String> parameters = new ArrayList<>(10);

            while (matcher.find()) {
                if (matcher.group("QUOTE") != null) quotes.add(matcher.group("QUOTE"));
                if (matcher.group("PARAMETER") != null) parameters.add(matcher.group("PARAMETER"));
            }

            List<Text> quotesText = quotes.stream().map(str -> {
                Text text = new Text(str);
                text.getStyleClass().add("quote");
                return text;
            }).collect(Collectors.toList());

            List<Text> parametersText = parameters.stream().map(str -> {
                Text text = new Text(str);
                text.getStyleClass().add("parameter");
                return text;
            }).collect(Collectors.toList());

            return null;

        }
    }
}
