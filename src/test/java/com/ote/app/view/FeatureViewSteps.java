package com.ote.app.view;

import com.ote.app.model.Description;
import com.ote.app.model.FeatureParser;
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

    private String feature;
    private IFeatureView view;

    @Given("a standard feature")
    public void a_standard_feature() throws Throwable {

        this.feature = STANDARD_FEATURE;
    }


    @When("I load the feature into the view")
    public void I_load_the_feature_into_the_view() throws Throwable {

        /*this.view = new FeatureViewMock();
        this.view.setFeature(FeatureParser.parseFeature(STANDARD_FEATURE));*/
    }

    @Then("the feature's title from view should be \"(.*)\"")
    public void the_feature_title_from_view_should_be(String title) throws Throwable {

        Assertions.assertThat(title).isEqualTo(this.view.getTitle());
    }

    @Then("the feature's description from view should be:")
    public void the_feature_s_description_from_view_should_be(String newDescription) throws Throwable {

        newDescription = format(newDescription);
        Description description = FeatureParser.parseDescription(newDescription);

        Assertions.assertThat(description.getLine().
                stream().
                map(l -> l.getContent()).
                collect(Collectors.joining("\r\n"))).isEqualTo(this.view.getDescription());
    }

    @When("I update the feature's title from view to \"(.*)\"")
    public void I_update_the_feature_s_title_from_view_to(String newTitle) throws Throwable {
        this.view.setTitle(newTitle);
    }

    @When("I update the feature's description from view to:")
    public void I_update_the_feature_s_description_from_view_to(String newDescription) throws Throwable {

       /* newDescription = format(newDescription);
        this.view.setDescription(newDescription);*/
    }

    private String format(String newDescription) {
        return newDescription.replaceAll("(\r\n|\n\r|\r|\n)", "\r\n");
    }

    @Then("the feature is:")
    public void the_feature_is(String featureAsText) throws Throwable {

        Assertions.
                assertThat(format(featureAsText.replaceAll("(\t)", "").replaceAll(" ", "").trim())).
                isEqualTo(format(this.feature.replaceAll("(\t)", "").replaceAll(" ", "").trim()));
    }
}
