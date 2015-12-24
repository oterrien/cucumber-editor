package com.ote.app.view.scenario;

import com.ote.app.model.Scenario;
import com.ote.app.view.IView;

/**
 * Created by Olivier on 24/12/2015.
 */
public interface IScenarioView extends IView<Scenario> {

    String getTitle();

    void setTitle(String title);

    String getSteps();

    void setSteps(String steps);
}
