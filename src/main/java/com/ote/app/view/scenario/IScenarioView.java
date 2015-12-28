package com.ote.app.view.scenario;

import com.ote.app.model.Scenario;
import com.ote.app.model.ScenarioOutline;
import com.ote.app.model.ScenarioType;
import com.ote.app.view.IView;

/**
 * Created by Olivier on 24/12/2015.
 */
public interface IScenarioView extends IView<ScenarioType> {

    enum Type {
        SCENARIO("Scenario: "), SCENARIO_OUTLINE("Scenario Outline: "), BACKGROUND("Background: ");

        private String value;

        Type(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Type valueOf(ScenarioType scenario) {
            if (scenario instanceof Scenario) {
                return SCENARIO;
            }
            if (scenario instanceof ScenarioOutline) {
                return SCENARIO_OUTLINE;
            }
            if (scenario instanceof ScenarioOutline) {
                return BACKGROUND;
            }
            return null;
        }
    }

    String getTitle();

    void setTitle(String title);

    String getSteps();

    void setSteps(String steps);

    Type getType();

    void setType(Type type);
}
