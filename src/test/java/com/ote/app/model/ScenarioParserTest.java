package com.ote.app.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

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

        Scenario scenario = ScenarioConverter.getInstance().getParser().parse(SCENARIO);

        Assertions.assertThat(scenario).isNotNull();
        Assertions.assertThat(scenario.getTitle()).isEqualTo("Wilson posts to his own blog");
        Assertions.assertThat(scenario.getSteps().getLineOrDefinition().size() == 5);
    }

    @Test
    public void steps_text_should_be_loaded_into_model() {

        Steps steps = ScenarioStepsConverter.getInstance().getParser().parse(STEPS);

        Assertions.assertThat(steps.getLineOrDefinition().size() == 5);
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(0)).getType() == StepType.GIVEN);
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(0)).getContent().equals("a global administrator named \"Greg\""));
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(1)).getType() == StepType.AND);
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(1)).getContent().equals("a customer named \"Wilson\""));
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(2)).getType() == StepType.AND);
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(2)).getContent().equals("a blog named \"Expensive Therapy\" owned by \"Wilson\""));
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(3)).getType() == StepType.WHEN);
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(3)).getContent().equals("I open the blog"));
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(4)).getType() == StepType.THEN);
        Assertions.assertThat(((Step) steps.getLineOrDefinition().get(4)).getContent().equals("the blog is called"));
    }
}
