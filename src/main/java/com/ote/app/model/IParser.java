package com.ote.app.model;

/**
 * Created by Olivier on 24/12/2015.
 */
public interface IParser<M> {

    default M parse(String text){
        return parse(text.split("(\r\n|\n\r|\r|\n)"));
    }

    M parse(String... text);
}
