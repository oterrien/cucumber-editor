package com.ote.app.model;

import javafx.scene.text.Text;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 24/12/2015.
 */
public class ScenarioParserTest {

    private static final String STEPS =
            "\tgiven a global administrator named \"Greg\"\r\n" +
                    "\tand a customer named \"Wilson\"\r\n" +
                    "\tand a blog named \"Expensive Therapy\" owned by \"Wilson\"\r\n" +
                    "\twhen I open the blog\r\n" +
                    "\tthen the blog is called\r\n";

    private static final String SCENARIO = "Scenario: Wilson posts to his own blog\r\n" +
            STEPS;

    @Test
    public void scenario_text_should_be_loaded_into_model() {

        ScenarioType scenario = ScenarioConverter.getInstance().getParser().parse(SCENARIO);

        Assertions.assertThat(scenario).isNotNull();
        Assertions.assertThat(scenario.getTitle()).isEqualTo("Wilson posts to his own blog");
        Assertions.assertThat(scenario.getSteps().getLineOrDefinition().size() == 5);
    }

    @Test
    public void steps_text_should_be_loaded_into_model() {

        Steps steps = ScenarioStepsConverter.getInstance().getParser().parse(STEPS);

        Assertions.assertThat(steps.getLineOrDefinition().size() == 5);
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(0)).getStep().getType() == StepType.GIVEN);
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(0)).getStep().getContent().equals("a global administrator named \"Greg\""));
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(1)).getStep().getType() == StepType.AND);
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(1)).getStep().getContent().equals("a customer named \"Wilson\""));
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(2)).getStep().getType() == StepType.AND);
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(2)).getStep().getContent().equals("a blog named \"Expensive Therapy\" owned by \"Wilson\""));
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(3)).getStep().getType() == StepType.WHEN);
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(3)).getStep().getContent().equals("I open the blog"));
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(4)).getStep().getType() == StepType.THEN);
        Assertions.assertThat(((Definition) steps.getLineOrDefinition().get(4)).getStep().getContent().equals("the blog is called"));
    }

    //@Test
    public void scenario_model_should_be_formatted() {

        ScenarioType scenario = new Scenario();
        scenario.setTitle("Wilson posts to his own blog");

        Steps steps = new Steps();

        Definition definition = new Definition();
        Step step = new Step();
        step.setContent("a global administrator named \"Greg\"");
        step.setType(StepType.GIVEN);
        definition.setStep(step);
        steps.getLineOrDefinition().add(definition);

        definition = new Definition();
        step = new Step();
        step.setContent("a customer named \"Wilson\"");
        step.setType(StepType.AND);
        definition.setStep(step);
        steps.getLineOrDefinition().add(definition);

        definition = new Definition();
        step = new Step();
        step.setContent("a blog named \"Expensive Therapy\" owned by \"Wilson\"");
        step.setType(StepType.AND);
        definition.setStep(step);
        steps.getLineOrDefinition().add(definition);

        definition = new Definition();
        step = new Step();
        step.setContent("I open the blog");
        step.setType(StepType.WHEN);
        definition.setStep(step);
        steps.getLineOrDefinition().add(definition);

        definition = new Definition();
        step = new Step();
        step.setContent("the blog is called");
        step.setType(StepType.THEN);
        definition.setStep(step);
        steps.getLineOrDefinition().add(definition);

        scenario.setSteps(steps);

        Assertions.assertThat(ScenarioStepsConverter.getInstance().getFormatter().format(steps, true)).isEqualTo(STEPS.replaceAll("\"", "'"));
        Assertions.assertThat(ScenarioConverter.getInstance().getFormatter().format(scenario, true)).isEqualTo(SCENARIO.replaceAll("\"", "'"));
    }

    private static final String STEP_WITH_TABLE = "given a step with table\r\n" +
            "| param1 | param2 |\r\n" +
            "| value11 | value12 |\r\n" +
            "| value21 | value22 |\r\n";

    @Test
    public void step_with_table_should_be_loaded_into_model() {

        Steps steps = ScenarioStepsConverter.getInstance().getParser().parse(STEP_WITH_TABLE);

        Assertions.assertThat(steps).isNotNull();
        Assertions.assertThat(steps.getLineOrDefinition().size()).isEqualTo(1);
        Assertions.assertThat(steps.getLineOrDefinition().get(0)).isNotNull();
        Assertions.assertThat(steps.getLineOrDefinition().get(0)).isInstanceOf(Definition.class);

        Optional<Step> step = steps.getLineOrDefinition().stream().filter(elem -> elem instanceof Definition).
                map(elem -> (Definition) elem).map(def -> def.getStep()).findFirst();

        Assertions.assertThat(step.isPresent()).isEqualTo(true);
        Assertions.assertThat(step.get().getContent()).isEqualTo("a step with table");
        Assertions.assertThat(step.get().getType()).isEqualTo(StepType.GIVEN);


        Optional<Table> table = steps.getLineOrDefinition().stream().filter(elem -> elem instanceof Definition).
                map(elem -> (Definition) elem).map(def -> def.getTable()).findFirst();

        Assertions.assertThat(table.isPresent()).isEqualTo(true);
        Assertions.assertThat(table.get().getHeader().getCell().stream().collect(Collectors.joining(";"))).
                isEqualTo("param1;param2");
        Assertions.assertThat(table.get().getRow().size()).isEqualTo(2);
        Assertions.assertThat(table.get().getRow().get(0).getCell().stream().collect(Collectors.joining(";"))).
                isEqualTo("value11;value12");
        Assertions.assertThat(table.get().getRow().get(1).getCell().stream().collect(Collectors.joining(";"))).
                isEqualTo("value21;value22");
    }

    private static final String STEP_WITH_TABLE_INDENTED = "given a step with table\r\n" +
            "| param1  | param2  |\r\n" +
            "| value11 | value12 |\r\n" +
            "| value21 | value22 |\r\n";

    //@Test
    public void step_with_table_should_be_formatted() {

        Steps steps = new Steps();
        Definition definition = new Definition();
        Step step = new Step();
        step.setContent("a step with table");
        step.setType(StepType.GIVEN);
        definition.setStep(step);

        Table table = new Table();
        Row header = new Row();
        header.getCell().addAll(Arrays.asList("param1", "param2"));
        table.setHeader(header);
        Row row = new Row();
        row.getCell().addAll(Arrays.asList("value11", "value12"));
        table.getRow().add(row);
        row = new Row();
        row.getCell().addAll(Arrays.asList("value21", "value22"));
        table.getRow().add(row);

        definition.setTable(table);
        steps.getLineOrDefinition().add(definition);

        Assertions.assertThat(ScenarioStepsConverter.getInstance().getFormatter().format(steps, false)).
                isEqualTo(STEP_WITH_TABLE_INDENTED);
    }

    @Test
    public void tab_should_be_indented_with_max_value() {

        Collection<String> param = Arrays.asList(
                "|color | value|result |",
                "|yellow | true|foo|",
                "|red|true|a big result |");

        Collection<String> res = Arrays.asList(
                "| color  | value | result       |",
                "| yellow | true  | foo          |",
                "| red    | true  | a big result |");

        Assertions.assertThat(TableConverter.Helper.pad(TableConverter.Helper.extract(param, "\\|"), " | ", "| ", " |")).
                usingFieldByFieldElementComparator().
                containsExactly(res.toArray(new String[0]));
    }
}
