package com.ote.app.model;

/**
 * Created by Olivier on 24/12/2015.
 */
public interface IModelConverter<M> {

    IParser<M> getParser();

    IFormatter<M> getFormatter();

    IDisplayFormatter<M> getDisplayFormatter();
}
