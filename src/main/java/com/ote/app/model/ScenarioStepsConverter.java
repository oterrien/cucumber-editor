package com.ote.app.model;

import com.sun.xml.internal.ws.util.StringUtils;
import javafx.scene.Node;
import javafx.scene.text.Text;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                String stepStr = text[i].trim().replaceAll("\"", "'");

                Pattern pattern = Pattern.compile("^([Gg]iven|[Ww]hen|[Tt]hen|[Aa]nd|[Bb]ut)(.*)$");
                Matcher matcher = pattern.matcher(stepStr);

                if (matcher.find()) {
                    steps.getLineOrDefinition().add(parseDefinition(matcher.group(1).toLowerCase(), matcher.group(2)));
                } else if (stepStr.startsWith("|")) {

                    Object previousLine = steps.getLineOrDefinition().get(steps.getLineOrDefinition().size() - 1);

                    if (previousLine instanceof Definition) {

                        Definition stepDef = (Definition) previousLine;

                        List<String> table = new ArrayList<>(10);
                        table.add(stepStr);
                        while (i + 1 < text.length && text[i + 1].trim().startsWith("|")) {
                            stepStr = text[++i].trim();
                            table.add(stepStr);
                        }
                        stepDef.setTable(TableConverter.getInstance().getParser().parse(table.toArray(new String[0])));
                    } else {
                        Line line = new Line();
                        line.setIsCommented(true);
                        line.setContent("#" + stepStr.trim());
                        steps.getLineOrDefinition().add(line);
                        while (i + 1 < text.length && text[i + 1].trim().startsWith("|")) {
                            stepStr = text[++i].trim();
                            line = new Line();
                            line.setIsCommented(true);
                            line.setContent("#" + stepStr.trim());
                            steps.getLineOrDefinition().add(line);
                        }
                    }
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

        private Definition parseDefinition(String stepName, String stepText) {
            Definition stepDef = new Definition();
            Step step = new Step();
            step.setType(StepType.fromValue(stepName.trim()));
            step.setContent(stepText.trim());
            stepDef.setStep(step);
            return stepDef;
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
            sb.append((isIndented ? "\t" : "")).append(line.getContent().replaceAll("\"", "'")).append("\r\n");
            return sb.toString();
        }

        private String format(Definition stepDef, boolean isIndented) {

           StringBuilder sb = new StringBuilder();
            sb.append(isIndented ? "\t" : "").
                    append( StringUtils.capitalize(stepDef.getStep().getType().value().toLowerCase().replaceAll("\"", "'"))).
                    append(" ").
                    append(stepDef.getStep().getContent().replaceAll("\"", "'")).append("\r\n");

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
                    list.add(format((Line) lineOfDefinition));
                } else {
                    list.addAll(format((Definition) lineOfDefinition));
                }
            });

            return list;
        }

        private Node format(Line line) {

            String content = line.getContent();
            Text text = new Text(content);
            text.getStyleClass().add(content.startsWith("#") ? "comment" : "description");
            return text;
        }

        private List<Node> format(Definition stepDef) {

            List<Node> textList = new ArrayList<>(100);

            Step step = stepDef.getStep();
            Text stepName = new Text(StringUtils.capitalize(step.getType().value().toLowerCase()));
            stepName.getStyleClass().add("step");
            textList.add(stepName);
            textList.add(new Text("\t\t"));

            String content = step.getContent();

            Pattern pattern = Pattern.compile("(?<QUOTE>'([^']+[^'])')|(?<PARAMETER><([^<]+[^>])>)");
            Matcher matcher = pattern.matcher(content);

            Map<String, List<String>> map = new HashMap<>(10);

            while (matcher.find()) {
                if (matcher.group("QUOTE") != null) {
                    content = extract(content, matcher, "QUOTE", map);
                } else if (matcher.group("PARAMETER") != null) {
                    content = extract(content, matcher, "PARAMETER", map);
                }
            }

            for (String word : content.split("\\p{Space}")) {
                if (word.startsWith("@QUOTE")) {
                    textList.add(getText(word, map.get("QUOTE"), "quote"));
                } else if (word.startsWith("@PARAMETER")) {
                    textList.add(getText(word, map.get("PARAMETER"), "parameter"));
                } else {
                    Text text = new Text(word + " ");
                    text.getStyleClass().add("description");
                    textList.add(text);
                }
            }

            if (stepDef.getTable() != null) {
                textList.add(new Text("\r\n"));
                textList.addAll(TableConverter.getInstance().getDisplayFormatter().format(stepDef.getTable()));
            }

            return textList;
        }

        private String extract(String step, Matcher matcher, String group, Map<String, List<String>> map) {

            List<String> list = map.get(group);
            if (list == null) {
                list = new ArrayList<>(10);
                map.put(group, list);
            }

            String value = matcher.group(group);
            list.add(value);
            return step.replaceAll(value, "@" + group + "(" + (list.size() - 1) + ")");
        }

        private Text getText(String word, List<String> list, String cssStyle) {

            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(word);
            matcher.find();

            Text text = new Text(list.get(Integer.parseInt(matcher.group(0))) + " ");
            text.getStyleClass().add(cssStyle);
            return text;
        }
    }
}
