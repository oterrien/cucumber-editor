package com.ote.app.model;

import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

/**
 * Created by Olivier on 24/12/2015.
 */
public final class FeatureDescriptionConverter extends AbstractConverter<Description> implements IModelConverter<Description> {

    private static final FeatureDescriptionConverter INSTANCE = new FeatureDescriptionConverter();

    private FeatureDescriptionConverter() {
        super(new Parser(), new Formatter(), new DisplayFormatter());
    }

    public static FeatureDescriptionConverter getInstance() {
        return INSTANCE;
    }

    private static class Parser implements IParser<Description> {

        @Override
        public Description parse(String... text) {

            Description description = new Description();

            IntStream.range(0, text.length).
                    forEach(i -> {
                        Line line = new Line();
                        line.setContent(text[i].replaceAll("(\t)", "").trim());
                        if (line.getContent().startsWith("#")) {
                            line.setIsCommented(true);
                        }
                        description.getLine().add(line);
                    });

            return description;
        }
    }

    private static class Formatter implements IFormatter<Description> {

        @Override
        public String format(Description model) {

            StringBuilder sb = new StringBuilder();
            model.getLine().stream().
                    forEach(l -> sb.append(l.isIsCommented() ? "# " : "").append(l.getContent()).append("\r\n"));
            return sb.toString();
        }
    }

    private static class DisplayFormatter implements IDisplayFormatter<Description> {

        @Override
        public Collection<Node> format(Description model) {

            Collection<Node> list = new ArrayList<>(10);

            model.getLine().stream().map(l -> l.getContent()).forEach(content -> {
                list.add(new Text("\r\n"));
                Text text = new Text(content);
                text.getStyleClass().add(content.startsWith("#") ? "comment" : "description");
                list.add(text);
            });

            return list;
        }
    }
}
