package com.ote.app.model;

import javafx.scene.text.TextFlow;

/**
 * Created by Olivier on 24/12/2015.
 */
public interface IFormatter<M> {

    String format(M model);
}
