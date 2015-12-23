package com.ote.app.view.scenario2;

import com.ote.app.model.Steps;
import com.ote.app.view.IView;

/**
 * Created by Olivier on 21/12/2015.
 */
public interface IScenarioView extends IView {

    String getTitle();

    void setTitle(String title);

    Steps getSteps();

    void setSteps(Steps steps);
}
