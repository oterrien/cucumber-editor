package com.ote.app.view.scenario;

import com.ote.app.Mode;
import com.ote.app.model.Scenario;
import com.ote.app.model.ScenarioConverter;
import com.ote.app.view.AbstractEditableView;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 * Created by Olivier on 24/12/2015.
 */
public class ScenarioView extends AbstractEditableView<ScenarioPresenter, Scenario> implements IScenarioView {

    @FXML
    private TextFlow displayPane;

    @FXML
    private VBox editPane;

    @FXML
    private TextField title;

    @FXML
    private TextArea steps;

    @FXML
    public void initialize() {

        super.initialize(this.displayPane, this.editPane);

        // SHIFT + ENTER -> validate description and title
        this.steps.setOnKeyPressed(this::validateOnShitEnter);
        this.title.setOnKeyPressed(this::validateOnShitEnter);

        this.load();
    }

    @Override
    protected ScenarioPresenter createPresenter() {
        return new ScenarioPresenter(this);
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
    public String getSteps() {
        return this.steps.getText();
    }

    @Override
    public void setSteps(String steps) {
        this.steps.setText(steps);
    }

    @FXML
    public void onValidateClicked() {
        validate();
    }

    @FXML
    public void onCancelClicked() {
        cancel();
    }

    @FXML
    public void onDisplayPaneClicked(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            this.setMode(Mode.EDIT);
        }
    }

    protected void fillDisplayPane() {

        this.displayPane.getChildren().clear();

        Scenario scenario = this.getPresenter().getModel();
        this.displayPane.getChildren().addAll(ScenarioConverter.getInstance().getDisplayFormatter().format(scenario));
    }

    private static final String STANDARD_SCENARIO =
            "Scenario: Wilson posts to his own blog\r\n" +
                    "\tgiven a global administrator named \"Greg\"\r\n" +
                    "\tand a customer named \"Wilson\"\r\n" +
                    "\tand a blog named \"Expensive Therapy\" owned by \"Wilson\"\r\n" +
                    "\twhen I open the blog\r\n" +
                    "| toto | titi |\r\n" +
                    "| dodo | data |\r\n" +
                    "\tthen the blog is called\r\n";

    public void load() {

        this.setModel(ScenarioConverter.getInstance().getParser().parse(STANDARD_SCENARIO));
        this.setMode(Mode.DISPLAY);
    }
}
