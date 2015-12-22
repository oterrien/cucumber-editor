package com.ote.app.view.scenario;

import com.ote.app.model.Scenario;
import com.ote.app.view.AbstractPresenter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by Olivier on 21/12/2015.
 */
public final class ScenarioPresenter extends AbstractPresenter {

    private ScenarioPresenter() {

    }

    private static final ScenarioPresenter INSTANCE = new ScenarioPresenter();

    public static ScenarioPresenter getInstance() {
        return INSTANCE;
    }

    private ObjectProperty<Scenario> scenario = new SimpleObjectProperty<>();

    public ObjectProperty<Scenario> scenarioProperty() {
        return this.scenario;
    }

    public void register(IScenarioView view) {

        super.register(view);

        //..
    }

    public void register(IScenarioEditView view) {

        this.register((IScenarioView) view);

        //..

        super.register(view);

    }

}