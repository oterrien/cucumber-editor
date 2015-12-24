package com.ote.app.view.scenario;

import com.ote.app.model.Scenario;
import com.ote.app.model.ScenarioConverter;
import com.ote.app.model.ScenarioStepsConverter;
import com.ote.app.view.AbstractPresenter;
import com.ote.app.view.IPresenter;

/**
 * Created by Olivier on 24/12/2015.
 */
public class ScenarioPresenter extends AbstractPresenter<Scenario, IScenarioView> implements IPresenter<Scenario, IScenarioView> {

    public ScenarioPresenter(IScenarioView view) {
        super(view);
    }

    @Override
    protected void fillView(Scenario model) {
        this.getView().setTitle(model.getTitle());
        this.getView().setSteps(ScenarioStepsConverter.getInstance().getFormatter().format(model.getSteps()));
    }

    @Override
    protected void fillModel() {
        this.setModel(ScenarioConverter.getInstance().getParser().
                parse(this.getView().getTitle(), this.getView().getSteps()));

    }
}
