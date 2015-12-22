package com.ote.app.view.feature2.edit;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by Olivier on 22/12/2015.
 */
public class FeatureEditView implements IFeatureEditView {

    @FXML
    private VBox root;

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    private FeatureEditPresenter presenter;

    private MultiConsumer<Void> showCommand = new MultiConsumer<>();

    private MultiConsumer<Void> hideCommand = new MultiConsumer<>();

    private MultiConsumer<Void> validateCommand = new MultiConsumer<>();

    private MultiConsumer<Void> cancelCommand = new MultiConsumer<>();



    @FXML
    public void initialize() {

        this.presenter = new FeatureEditPresenter(this);

        /**
         * TODO CF FeatureView / FeaturePresenter Plus haut niveau, qui pilote quelle vue (display ou edit) afficher
         */

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
    public String getDescription() {
        return this.description.getText();
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @FXML
    @Override
    public void validate() {
        validateCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getValidateCommand() {
        return this.validateCommand;
    }

    @FXML
    @Override
    public void cancel() {
        cancelCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getCancelCommand() {
        return this.cancelCommand;
    }

    @Override
    public void show() {
        this.showCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getShowCommand() {
        return this.showCommand;
    }

    @Override
    public void hide() {
        this.hideCommand.accept(null);
    }

    @Override
    public MultiConsumer<Void> getHideCommand() {
        return this.hideCommand;
    }

    @Override
    public Mode getMode() {
        return Mode.EDIT;
    }

}
