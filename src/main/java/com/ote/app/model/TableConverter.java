package com.ote.app.model;

import javafx.scene.Node;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
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

            if (!text[0].startsWith("|")) {
                return null;
            }
            Table table = new Table();
            table.setHeader(parseRow(text[0]));
            IntStream.range(1, text.length).forEach(i -> table.getRow().add(parseRow(text[i])));
            return table;
        }

        private Row parseRow(String content) {

            if (content.startsWith("|")) {

                Row row = new Row();
                Helper.extract(content, "\\|").stream().forEach(s -> row.getCell().add(s));
                return row;
            }
            return null;
        }
    }

    private static class Formatter implements IFormatter<Table> {

        @Override
        public String format(Table model, boolean isIndented) {

            return Helper.pad(Helper.extract(model), " | ", (isIndented ? "\t| " : "| "), " |").
                    stream().collect(Collectors.joining("\r\n"));
        }
    }

    private static class DisplayFormatter implements IDisplayFormatter<Table> {

        @Override
        public Collection<Node> format(Table model) {

            Collection<Node> list = new ArrayList<>(10);

            Collection<String> format = Helper.pad(Helper.extract(model), " | ", "| ", " |");

            System.out.println(format);

            list.add(new Text("\t"));
            Text text = new Text(format.stream().findFirst().get());
            text.getStyleClass().add("table_header");
            list.add(text);

            List<String> rows = format.stream().collect(Collectors.toList());

            IntStream.range(1, rows.size()).forEach(i -> {
                list.add(new Text("\r\n"));
                list.add(new Text("\t"));
                Text line = new Text(rows.get(i));
                line.getStyleClass().add("table_element");
                list.add(line);
            });

            return list;
        }
    }

    public static final class Helper {

        public static Collection<List<String>> extract(Table model) {

            return extract(model.getHeader().getCell(),
                    model.getRow().stream().map(row -> row.getCell()).collect(Collectors.toList()));
        }

        public static Collection<List<String>> extract(Collection<String> table, String regexp) {

            return table.stream().map(str -> extract(str, regexp)).collect(Collectors.toList());
        }

        public static List<String> extract(String data, String regexp) {

            String[] strSplit = data.split(regexp);
            return Arrays.asList(strSplit).subList(1, strSplit.length).
                    stream().map(s -> s.trim()).collect(Collectors.toList());
        }

        public static Collection<List<String>> extract(List<String> header, List<List<String>> rows) {

            Collection<List<String>> list = new ArrayList<>(rows.size() + 1);
            if (header != null) {
                list.add(header);
            }

            if (rows != null) {
                rows.forEach(l -> list.add(l));
            }

            return list;
        }

        public static Collection<String> pad(Collection<List<String>> param, String delimiter, String startDelimiter, String endDelimiter) {

            Map<Integer, Integer> maxLengthPerColumn = new HashMap<>(10);

            param.forEach(e ->
                    IntStream.range(0, e.size()).forEach(i -> {
                        String curElem = e.get(i).trim();
                        int curLength = curElem.length();
                        Integer curMax = maxLengthPerColumn.get(i);
                        if (curMax != null) {
                            if (curLength > curMax) {
                                maxLengthPerColumn.put(i, curLength);
                            }
                        } else {
                            maxLengthPerColumn.put(i, curLength);
                        }
                    })
            );

            return param.stream().
                    map(e -> {
                        IntStream.range(0, e.size()).forEach(i -> {
                            StringBuilder sb = new StringBuilder().
                                    append(StringUtils.rightPad(e.get(i).trim(), maxLengthPerColumn.get(i)));
                            e.set(i, sb.toString());
                        });
                        return e;
                    }).
                    map(e -> e.stream().collect(Collectors.joining(delimiter, startDelimiter, endDelimiter))).
                    collect(Collectors.toList());
        }
    }
}
