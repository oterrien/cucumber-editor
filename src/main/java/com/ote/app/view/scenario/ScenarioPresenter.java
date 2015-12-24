package com.ote.app.view.scenario;

import com.ote.app.model.Definition;
import com.ote.app.model.Scenario;
import com.ote.app.model.Step;
import com.ote.app.view.AbstractPresenter;
import com.ote.app.view.IPresenter;

import java.util.stream.Collectors;

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
        this.getView().setSteps(model.getSteps().getLineOrDefinition().stream().
                filter(l -> l instanceof Definition).
                map(l -> ((Definition) l).getStep().getContent()).
                collect(Collectors.joining("\r\b")));
    }

    @Override
    protected void fillModel() {


    }
}
