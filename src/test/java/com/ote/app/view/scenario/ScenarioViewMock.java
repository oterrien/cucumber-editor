package com.ote.app.view.scenario;

import com.ote.app.model.Scenario;
import com.ote.app.model.ScenarioType;
import com.ote.app.view.AbstractView;

/**
 * Created by Olivier on 26/12/2015.
 */
public class ScenarioViewMock extends AbstractView<ScenarioPresenter, ScenarioType> implements IScenarioView {

    private String title;

    private String steps;

    public ScenarioViewMock() {
        this.initialize();
    }

    @Override
    protected ScenarioPresenter createPresenter() {
        return new ScenarioPresenter(this);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSteps() {
        return this.steps;
    }

    @Override
    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public void setType(Type type) {

    }
}
