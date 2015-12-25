package com.ote.app.model;

import javafx.scene.Node;

import java.util.Collection;

/**
 * Created by Olivier on 25/12/2015.
 */
public interface IDisplayFormatter<M> {

    Collection<Node> format(M model);
}
