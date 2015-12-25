package com.ote.app.model;

import javafx.scene.Node;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Olivier on 25/12/2015.
 */
public final class TableConverter extends AbstractConverter<Table> implements IModelConverter<Table> {

    private static final TableConverter INSTANCE = new TableConverter();

    private TableConverter() {
        super(new Parser(), new Formatter(), new DisplayFormatter());
    }

    public static TableConverter getInstance() {
        return INSTANCE;
    }

    private static class Parser implements IParser<Table> {

        @Override
        public Table parse(String... text) {

            if (!text[0].trim().startsWith("|")) {
                return null;
            }
            Table table = new Table();
            table.setHeader(parseRow(text[0].trim()));

            IntStream.range(1, text.length).forEach(i -> {
                String current = text[i].trim();
                table.getRow().add(parseRow(current));
            });

            return table;
        }

        private Row parseRow(String content) {
            if (content.trim().startsWith("|")) {
                String[] cell = content.trim().split("|");
                Row row = new Row();
                row.getCell().addAll(Arrays.asList(cell));
                return row;
            }
            return null;
        }
    }

    private static class Formatter implements IFormatter<Table> {

        @Override
        public String format(Table model) {

            StringBuilder sb = new StringBuilder();
            sb.append(model.getHeader().getCell().stream().collect(Collectors.joining("|"))).append("\r\n");
            model.getRow().forEach(r -> sb.append(r.getCell().stream().collect(Collectors.joining("|"))).append("\r\n"));
            return sb.toString();
        }
    }

    private static class DisplayFormatter implements IDisplayFormatter<Table> {

        @Override
        public Collection<Node> format(Table model) {

            Collection<Node> list = new ArrayList<>(10);

            model.getHeader().getCell().forEach(c -> {
                Text text = new Text(c);
                text.getStyleClass().add("table");
                list.add(text);
            });
            list.add(new Text("\r\n"));

            return list;
        }
    }
}
