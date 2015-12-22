package com.ote.app.view.feature;

import com.ote.app.view.AbstractEditView;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 19/12/2015.
 */
public final class FeatureEditView extends AbstractEditView<VBox> implements IFeatureEditView {

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    @FXML
    @Override
    public void initialize() {

        FeaturePresenter.getInstance().register(this);

        super.initialize();

        // SHIFT + ENTER -> validate description and title
        description.setOnKeyPressed(this::validateEditionOnShiftEnter);
        title.setOnKeyPressed(this::validateEditionOnShiftEnter);
    }

    /**
     * SHIFT + ENTER -> validate description and title
     */
    private void validateEditionOnShiftEnter(KeyEvent event) {
        if (event.isShiftDown() && event.getCode() == KeyCode.ENTER) {
            validate();
        }
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
    public Collection<String> getDescription() {
        return Arrays.asList(this.description.getText().split("(\r\n|\n\r|\r|\n)"));
    }

    @Override
    public void setDescription(Collection<String> description) {
        this.description.setText(description.stream().collect(Collectors.joining("\r\n")));
    }
}
