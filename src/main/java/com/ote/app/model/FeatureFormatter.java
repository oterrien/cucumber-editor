package com.ote.app.model;

import java.util.stream.Collectors;

/**
 * Created by Olivier on 24/12/2015.
 */
public class FeatureFormatter {

    public static String format(Feature feature) {

        return format(feature.getTitle(), feature.getDescription().getLine().
                stream().map(l -> l.getContent()).collect(Collectors.joining("\r\n")));
    }

    public static String format(String title, String description){

        StringBuilder sb = new StringBuilder("Feature: ").
                append(title).
                append("\r\n").
                append(description);

        return sb.toString();
    }
}
