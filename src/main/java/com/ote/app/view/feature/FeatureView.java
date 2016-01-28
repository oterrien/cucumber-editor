package com.ote.app.view.feature;

import com.ote.app.Mode;
import com.ote.app.model.Description;
import com.ote.app.model.Feature;
import com.ote.app.model.FeatureConverter;
import com.ote.app.model.FeatureDescriptionConverter;
import com.ote.app.view.AbstractEditableView;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by Olivier on 22/12/2015.
 */
public class FeatureView extends AbstractEditableView<FeaturePresenter, Feature> implements IFeatureView {

    @FXML
    private TextFlow displayPane;

    @FXML
    private VBox editPane;

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    @FXML
    private Separator separator;

    @FXML
    public void initialize() {

        super.initialize(this.displayPane, this.editPane);

        // SHIFT + ENTER -> validate description and title
        this.description.setOnKeyPressed(this::validateOnShitEnter);
        this.title.setOnKeyPressed(this::validateOnShitEnter);

        this.load();
    }

    @Override
    protected FeaturePresenter createPresenter() {
        return new FeaturePresenter(this);
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

    @Override
    protected void fillDisplayPane() {

        this.displayPane.getChildren().clear();

        Feature feature = this.getPresenter().getModel();
        this.displayPane.getChildren().addAll(FeatureConverter.getInstance().getDisplayFormatter().format(feature));
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

    @FXML
    private void onMouseEvent(MouseEvent event) {

        if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            this.editPane.getScene().setCursor(Cursor.V_RESIZE);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            this.editPane.getScene().setCursor(Cursor.DEFAULT);
        } else if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            this.editPane.getScene().setCursor(Cursor.V_RESIZE);
            this.separator.getStylesheets().add("com/ote/app/view/separator.css");
            Pane parent = (Pane) this.separator.getParent();
            parent.setPrefHeight(parent.getHeight() + event.getY());
        } else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            this.separator.getStylesheets().removeAll("com/ote/app/view/separator.css");
        }
    }

    private static final String STANDARD_FEATURE =
            "Feature: Feature View Management\r\n" +
                    "\tIn order to test the feature view\r\n" +
                    "\tAs a user\r\n" +
                    "\tI want to create new feature and update data\r\n";

    public void load() {

        this.setModel(FeatureConverter.getInstance().getParser().parse(STANDARD_FEATURE));
        this.setMode(Mode.DISPLAY);
    }
}
