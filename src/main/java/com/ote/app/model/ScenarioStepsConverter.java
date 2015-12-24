package com.ote.app.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

            Steps steps = new Steps();

            IntStream.range(0, text.length).
                    forEach(i -> {
                        String stepStr = text[i].trim();

                        Pattern pattern = Pattern.compile("^([Gg]iven|[Ww]hen|[Tt]hen|[Aa]nd|[Bb]ut)(.*)$");
                        Matcher matcher = pattern.matcher(stepStr);

                        if (matcher.find()) {

                            Step step = new Step();
                            step.setType(StepType.fromValue(matcher.group(1).trim()));
                            step.setContent(matcher.group(2).trim());
                            steps.getLineOrDefinition().add(step);

                        } else if (stepStr.startsWith("|")) {

                            Step step = (Step) steps.getLineOrDefinition().get(steps.getLineOrDefinition().size() - 1);
                            // TODO table

                        } else {
                            Line line = new Line();
                            line.setContent(text[i].replaceAll("(\t)", "").trim());
                            if (stepStr.startsWith("#")) {
                                line.setIsCommented(true);
                            }
                            steps.getLineOrDefinition().add(line);
                        }
                    });

            return steps;
        }
    }

    private static class Formatter implements IFormatter<Steps> {

        @Override
        public String format(Steps model) {

            StringBuilder sb = new StringBuilder();

            model.getLineOrDefinition().stream().forEach(lineOrStep -> {

                if (lineOrStep instanceof Line) {
                    sb.append(format((Line)lineOrStep));
                } else {
                    sb.append(format((Step)lineOrStep));
                }
            });

            return sb.toString();
        }

        private String format(Line line){
            StringBuilder sb = new StringBuilder();
            sb.append(line.isIsCommented() ? "# " : "").append(line.getContent()).append("\r\n");
            return sb.toString();
        }

        private String format(Step step){

            StringBuilder sb = new StringBuilder();

            sb.append(step.getType().value().toLowerCase()).append(" ").append(step.getContent()).append("\r\n");
            // TODO table
            return sb.toString();
        }

    }
}
