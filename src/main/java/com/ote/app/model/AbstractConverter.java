package com.ote.app.model;

/**
 * Created by Olivier on 25/12/2015.
 */
public abstract class AbstractConverter<M> implements IModelConverter<M> {

    private IParser<M> parser;
    private IFormatter<M> formatter;
    private IDisplayFormatter<M> displayFormatter;

    protected AbstractConverter(IParser<M> parser, IFormatter<M> formatter, IDisplayFormatter<M> displayFormatter) {
        this.parser = parser;
        this.formatter = formatter;
        this.displayFormatter = displayFormatter;
    }

    @Override
    public IParser<M> getParser() {
        return this.parser;
    }

    @Override
    public IFormatter<M> getFormatter() {
        return this.formatter;
    }

    @Override
    public IDisplayFormatter<M> getDisplayFormatter() {
        return this.displayFormatter;
    }
}
