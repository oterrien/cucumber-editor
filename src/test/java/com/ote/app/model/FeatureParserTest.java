package com.ote.app.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by Olivier on 18/12/2015.
 */
public class FeatureParserTest {

    private static final String DESCRIPTION = "" +
            "\tIn order to test the feature view\r\n" +
            "\tAs a user\r\n" +
            "\tI want to create new feature and update data\r\n";

    private static final String FEATURE = "Feature: Feature View Management\r\n" + DESCRIPTION;

    @Test
    public void feature_text_should_be_loaded_into_model() {

        Feature feature = FeatureParser.parseFeature(FEATURE);

        Assertions.assertThat(feature).isNotNull();
        Assertions.assertThat(feature.getTitle()).isEqualTo("Feature View Management");
        Assertions.assertThat(feature.getDescription().getLine().get(0).getContent()).isEqualTo("In order to test the feature view");
        Assertions.assertThat(feature.getDescription().getLine().get(1).getContent()).isEqualTo("As a user");
        Assertions.assertThat(feature.getDescription().getLine().get(2).getContent()).isEqualTo("I want to create new feature and update data");
    }

    @Test
    public void description_text_should_be_loaded_into_model() {

        Description description = FeatureParser.parseDescription(DESCRIPTION);

        Assertions.assertThat(description.getLine().get(0).getContent()).isEqualTo("In order to test the feature view");
        Assertions.assertThat(description.getLine().get(1).getContent()).isEqualTo("As a user");
        Assertions.assertThat(description.getLine().get(2).getContent()).isEqualTo("I want to create new feature and update data");
    }
}
