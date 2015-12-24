package com.ote.app.view.feature;

import com.ote.app.Mode;
import com.ote.app.model.Description;
import com.ote.app.model.Feature;
import com.ote.app.model.FeatureConverter;
import com.ote.app.model.FeatureDescriptionConverter;
import com.ote.app.view.AbstractEditableView;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

        Text name = new Text("Feature: ");
        name.getStyleClass().add("name");
        this.displayPane.getChildren().add(name);

        Text title = new Text(this.getTitle());
        title.getStyleClass().add("title");
        this.displayPane.getChildren().add(title);

        Description description = FeatureDescriptionConverter.getInstance().getParser().parse(this.getDescription());
        description.getLine().stream().map(l -> l.getContent()).forEach(content -> {
            this.displayPane.getChildren().add(new Text("\r\n"));
            Text text = new Text(content);
            text.getStyleClass().add(content.startsWith("#") ? "comment" : "description");
            this.displayPane.getChildren().add(text);
        });
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
