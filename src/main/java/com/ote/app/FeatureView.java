package com.ote.app;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Created by Olivier on 19/12/2015.
 */
public class FeatureView implements IFeatureView {

    private static final String STANDARD_FEATURE =
            "Feature: Feature View Management\r\n" +
                    "\tIn order to test the feature view\r\n" +
                    "\tAs a user\r\n" +
                    "\tI want to create new feature and update data\r\n";

    @FXML
    private TextField title;

    @FXML
    private TableView description;

    private Runnable onLoadAction;

    private FeaturePresenter presenter;

    @FXML
    public void initialize() {

        presenter = new FeaturePresenter(this, STANDARD_FEATURE);

        if (onLoadAction != null) {
            this.onLoadAction.run();
        }
    }

    @Override
    public String getTitle() {
        return this.title.getText();
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public List<String> getDescription() {

        return null;
    }

    @Override
    public Runnable getOnLoadFeature() {
        return onLoadAction;
    }

    @Override
    public void setOnLoadFeature(Runnable handler) {
        this.onLoadAction = handler;
    }
}
