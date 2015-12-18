package com.ote.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

/**
 * Created by Olivier on 17/12/2015.
 */
public interface IFeatureView {

    String getTitle();

    void setTitle(String title);

    List<String> getDescription();

    EventHandler<ActionEvent> getOnLoadFeature();

    void setOnLoadFeature(EventHandler<ActionEvent> handler);
}
