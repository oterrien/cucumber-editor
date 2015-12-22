package com.ote.app.view.scenario;

import com.ote.app.Mode;
import com.ote.app.view.AbstractDisplayView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;

/**
 * Created by Olivier on 21/12/2015.
 */
public final class ScenarioDisplayView extends AbstractDisplayView<TextFlow> implements IScenarioView {

    @FXML
    @Override
    public void initialize() {

        ScenarioPresenter.getInstance().register(this);

        super.initialize();

    }

    @Override
    protected void fill(TextFlow root) {

    }

    @FXML
    public void edit(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            ScenarioPresenter.getInstance().modeProperty().set(Mode.EDIT);
        }
    }

    @Override
    public Mode getMode() {
        return Mode.DISPLAY;
    }
}
