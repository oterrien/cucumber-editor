package com.ote.app.model;

/**
 * Created by Olivier on 24/12/2015.
 */
public interface IFormatter<M> {

    default String format(M model) {
        return format(model, false);
    }

    String format(M model, boolean isIndented);
}
