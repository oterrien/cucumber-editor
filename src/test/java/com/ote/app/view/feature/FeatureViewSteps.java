package com.ote.app.view.feature;

import com.ote.app.Mode;
import com.ote.app.model.Description;
import com.ote.app.model.Feature;
import com.ote.app.model.FeatureParser;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;

import java.util.Arrays;
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

    private IFeatureEditView view = new FeatureEditViewMock();
    private Feature feature;

    @Given("a standard feature")
    public void a_standard_feature() throws Throwable {

        this.feature = FeatureParser.parseFeature(STANDARD_FEATURE);
    }

    @When("I load the feature")
    public void I_load_the_feature() throws Throwable {

        FeaturePresenter.getInstance().featureProperty().set(this.feature);
        FeaturePresenter.getInstance().modeProperty().set(Mode.EDIT);
    }

    @Then("the feature's title from (.*) should be \"(.*)\"")
    public void the_feature_title_equals(String source, String title) throws Throwable {

        if (source.equalsIgnoreCase("view")) {
            Assertions.assertThat(title).isEqualTo(this.view.getTitle());
        } else if (source.equalsIgnoreCase("model")) {
            Assertions.assertThat(FeaturePresenter.getInstance().featureProperty().get().getTitle()).
                    isEqualTo(this.view.getTitle());
        }
    }

    @Then("the feature's description from (.*) should be:")
    public void the_feature_description_equals(String source, String newDescription) throws Throwable {

        Description description = FeatureParser.parseDescription(newDescription);

        if (source.equalsIgnoreCase("view")) {
            Assertions.assertThat(description.getLine().
                    stream().
                    map(l -> l.getContent()).
                    collect(Collectors.joining("\r\n"))).
                    isEqualTo(this.view.getDescription().stream().collect(Collectors.joining("\r\n")));
        } else if (source.equalsIgnoreCase("model")) {
            Assertions.assertThat(FeaturePresenter.getInstance().featureProperty().get().getDescription().getLine().
                    stream().
                    map(l -> l.getContent()).
                    collect(Collectors.joining("\r\n"))).
                    isEqualTo(this.view.getDescription().stream().collect(Collectors.joining("\r\n")));
        }
    }

    @When("I update the feature's title from (.*) to \"(.*)\"")
    public void I_update_the_feature_title(String source, String newTitle) throws Throwable {
        if (source.equalsIgnoreCase("view")) {
            this.view.setTitle(newTitle);
        } else if (source.equalsIgnoreCase("model")) {
            FeaturePresenter.getInstance().featureProperty().get().setTitle(newTitle);
        }
    }

    @When("I update the feature's description from (.*) to:")
    public void I_update_the_feature_description(String source, String newDescription) throws Throwable {

        newDescription = newDescription.replaceAll("(\r\n|\n\r|\r|\n)", "\r\n");

        if (source.equalsIgnoreCase("view")) {
            this.view.setDescription(Arrays.asList(newDescription.split("\r\n")));
        } else if (source.equalsIgnoreCase("model")) {
            FeaturePresenter.getInstance().featureProperty().get().setDescription(FeatureParser.parseDescription(newDescription));
        }
    }

    @When("I validate the (.*) update")
    public void I_validate_the_update(String source) throws Throwable {
        if (source.equalsIgnoreCase("view")) {
            this.view.validate();
        } else if (source.equalsIgnoreCase("model")) {
            FeaturePresenter.getInstance().modeProperty().set(Mode.INIT);
            FeaturePresenter.getInstance().modeProperty().set(Mode.EDIT);
        }
    }

    @Then("the feature should be:")
    public void the_feature_is(String featureAsText) throws Throwable {

        Feature feature = FeatureParser.parseFeature(featureAsText);

        Assertions.assertThat(feature.getTitle()).isEqualTo(this.feature.getTitle());
        Assertions.assertThat(feature.getDescription().getLine().stream().map(line -> line.getContent()).collect(Collectors.joining("\r\n"))).
                isEqualTo(this.feature.getDescription().getLine().stream().map(line -> line.getContent()).collect(Collectors.joining("\r\n")));
    }

    @Then("the feature in model should be:")
    public void the_feature_in_model_is(String featureAsText) throws Throwable {

        Feature feature = FeatureParser.parseFeature(featureAsText);

        Assertions.assertThat(feature.getTitle()).isEqualTo(FeaturePresenter.getInstance().featureProperty().get().getTitle());
        Assertions.assertThat(feature.getDescription().getLine().stream().map(line -> line.getContent()).collect(Collectors.joining("\r\n"))).
                isEqualTo(FeaturePresenter.getInstance().featureProperty().get().getDescription().getLine().stream().map(line -> line.getContent()).collect(Collectors.joining("\r\n")));
    }
}
