package com.ote.app.model;

import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Olivier on 26/12/2015.
 */
public class CollectorTest {

    @Test
    public void testCollector(){

        Collection<List<String>> list = Stream.of(
                Arrays.asList("param1 ", "param2 "),
                Arrays.asList("value11", "value12"),
                Arrays.asList("value21", "value22")
        ).collect(Collectors.toList());

        System.out.println(list);


        list.stream().forEach(l -> System.out.println(l.stream().collect(Collectors.joining(" | ", "| ", " |"))));
    }
}
