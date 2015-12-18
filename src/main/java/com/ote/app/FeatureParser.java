package com.ote.app;

import com.ote.app.model.Description;
import com.ote.app.model.Feature;
import com.ote.app.model.Line;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Olivier on 18/12/2015.
 */
public class FeatureParser {

    public static final Feature parseFeature(String text) {

        return parseFeature(text.split("(\r\n|\n\r|\r|\n)"));
    }

    public static final Feature parseFeature(String[] text) {

        if (text[0].indexOf("Feature:") != 0)
            return null;

        Feature feature = new Feature();
        feature.setTitle(text[0].replaceAll("Feature:", "").trim());
        feature.setDescription(parseDescription(Arrays.copyOfRange(text, 1, text.length)));

        return feature;
    }

    public static final Description parseDescription(String text) {

        return parseDescription(text.split("(\r\n|\n\r|\r|\n)"));
    }

    public static final Description parseDescription(String[] text) {

        Description description = new Description();

        IntStream.range(0, text.length).
                forEach(i -> {
                    Line line = new Line();
                    line.setContent(text[i].replaceAll("(\t)", "").trim());
                    description.getLine().add(line);
                });

        return description;
    }
}
