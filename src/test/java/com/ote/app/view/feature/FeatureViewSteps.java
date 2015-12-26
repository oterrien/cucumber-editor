package com.ote.app.view.feature;

import com.ote.app.model.Description;
import com.ote.app.model.Feature;
import com.ote.app.model.FeatureConverter;
import com.ote.app.model.FeatureDescriptionConverter;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;

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
    public void the_feature_title_equals(String expected) throws Throwable {

        Assertions.assertThat(expected).isEqualTo(this.view.getTitle());
    }

    @Then("the feature's description should be:")
    public void the_feature_description_equals(String expected) throws Throwable {

        Description expectedDescription = FeatureDescriptionConverter.getInstance().getParser().parse(expected);

        Description viewDescription = FeatureDescriptionConverter.getInstance().getParser().parse(this.view.getDescription());

        Assertions.assertThat(FeatureDescriptionConverter.getInstance().getFormatter().format(expectedDescription)).
                isEqualTo(FeatureDescriptionConverter.getInstance().getFormatter().format(viewDescription));
    }

    @When("I update the feature's title to \"(.*)\"")
    public void I_update_the_feature_title(String value) throws Throwable {

        this.view.setTitle(value);
    }

    @When("I update the feature's description to:")
    public void I_update_the_feature_description(String value) throws Throwable {

        this.view.setDescription(value);
    }

    @When("I validate the feature update")
    public void I_validate_the_feature_update() throws Throwable {

        this.view.validate();
    }

    @Then("the feature should be:")
    public void the_feature_is(String expected) throws Throwable {

        expected = FeatureConverter.getInstance().getFormatter().format(FeatureConverter.getInstance().getParser().parse(expected));
        Assertions.assertThat(expected).isEqualTo(FeatureConverter.getInstance().getFormatter().format(this.feature));
    }
}
