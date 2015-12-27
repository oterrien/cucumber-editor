package com.ote.app.view.scenario;


import com.ote.app.model.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;

import java.util.stream.Collectors;


/**
 * Created by Olivier on 26/12/2015.
 */
public class ScenarioViewSteps {

    private static final String STANDARD_SCENARIO =
            "Scenario: Wilson posts to his own blog\r\n" +
                    "\tgiven a global administrator named \"Greg\"\r\n" +
                    "\tand a customer named \"Wilson\"\r\n" +
                    "\tand a blog named \"Expensive Therapy\" owned by \"Wilson\"\r\n" +
                    "\twhen I open the blog\r\n" +
                    "\t| param1 | param2 |\r\n" +
                    "\t| value11 | value12 |\r\n" +
                    "\t| value21 | value22 |\r\n" +
                    "\tthen the blog is called\r\n";

    private IScenarioView view = new ScenarioViewMock();
    private ScenarioType scenario;

    @Given("a standard scenario")
    public void a_standard_scenario() throws Throwable {

        this.scenario= ScenarioConverter.getInstance().getParser().parse(STANDARD_SCENARIO);
    }

    @When("I load the scenario")
    public void I_load_the_scenario() throws Throwable {

        this.view.setModel(this.scenario);
    }

    @Then("the scenario's title should be \"(.*)\"")
    public void the_scenario_title_equals(String expected) throws Throwable {

        Assertions.assertThat(expected).isEqualTo(this.view.getTitle());
    }

    @Then("the scenario's steps should be:")
    public void the_scenario_steps_equals(String expected) throws Throwable {

        Steps expectedSteps = ScenarioStepsConverter.getInstance().getParser().parse(expected);

        Steps viewSteps = ScenarioStepsConverter.getInstance().getParser().parse(this.view.getSteps());

        Assertions.assertThat(ScenarioStepsConverter.getInstance().getFormatter().format(expectedSteps)).
                isEqualTo(ScenarioStepsConverter.getInstance().getFormatter().format(viewSteps));
    }

    @When("I update the scenario's title to \"(.*)\"")
    public void I_update_the_scenario_title(String value) throws Throwable {

        this.view.setTitle(value);
    }

    @When("I update the scenario's steps to:")
    public void I_update_the_scenario_step(String value) throws Throwable {

        this.view.setSteps(value);
    }

    @When("I validate the scenario update")
    public void I_validate_the_scenario_update() throws Throwable {

        this.view.validate();
    }

    @Then("the scenario should be:")
    public void the_scenario_is(String expected) throws Throwable {

        expected = ScenarioConverter.getInstance().getFormatter().format(ScenarioConverter.getInstance().getParser().parse(expected));
        Assertions.assertThat(expected ).isEqualTo(ScenarioConverter.getInstance().getFormatter().format(this.scenario));
    }
}
