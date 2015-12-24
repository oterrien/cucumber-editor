package com.ote.app.view.feature;

import com.ote.app.model.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 17/12/2015.
 */
public class FeatureViewSteps {

    private static final String STANDARD_FEATURE =
            "Feature: Feature View Management\r\n" +
                    "\tIn order to test the feature view\r\n" +
                    "\tAs a user\r\n" +
                    "\tI want to create new feature and update data\r\n";

    private IFeatureView view = new FeatureViewMock();
    private Feature feature;

    @Given("a standard feature")
    public void a_standard_feature() throws Throwable {

        this.feature = FeatureConverter.getInstance().getParser().parse(STANDARD_FEATURE);
    }

    @When("I load the feature")
    public void I_load_the_feature() throws Throwable {

        this.view.setModel(this.feature);
    }

    @Then("the feature's title should be \"(.*)\"")
    public void the_feature_title_equals(String title) throws Throwable {

        Assertions.assertThat(title).isEqualTo(this.view.getTitle());
    }

    @Then("the feature's description should be:")
    public void the_feature_description_equals(String newDescription) throws Throwable {

        Description description = FeatureDescriptionConverter.getInstance().getParser().parse(newDescription);

        Assertions.assertThat(description.getLine().
                stream().
                map(l -> l.getContent()).
                collect(Collectors.joining("\r\n"))).
                isEqualTo(this.view.getDescription());
    }

    @When("I update the feature's title to \"(.*)\"")
    public void I_update_the_feature_title(String newTitle) throws Throwable {

        this.view.setTitle(newTitle);
    }

    @When("I update the feature's description to:")
    public void I_update_the_feature_description(String newDescription) throws Throwable {

        newDescription = newDescription.replaceAll("(\r\n|\n\r|\r|\n)", "\r\n");
        this.view.setDescription(newDescription);
    }

    @When("I validate the update")
    public void I_validate_the_update() throws Throwable {

        this.view.validate();
    }

    @Then("the feature should be:")
    public void the_feature_is(String featureAsText) throws Throwable {

        Feature feature = FeatureConverter.getInstance().getParser().parse(featureAsText);

        Assertions.assertThat(feature.getTitle()).isEqualTo(this.feature.getTitle());
        Assertions.assertThat(feature.getDescription().getLine().stream().map(line -> line.getContent()).collect(Collectors.joining("\r\n"))).
                isEqualTo(this.feature.getDescription().getLine().stream().map(line -> line.getContent()).collect(Collectors.joining("\r\n")));
    }
}
