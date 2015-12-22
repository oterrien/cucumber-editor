package com.ote.app.view.scenario;

import com.ote.app.view.AbstractEditView;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Created by Olivier on 21/12/2015.
 */
public final class ScenarioEditView extends AbstractEditView<VBox> implements IScenarioEditView {

    @FXML
    public void initialize() {

        ScenarioPresenter.getInstance().register(this);

        super.initialize();

    }
}
