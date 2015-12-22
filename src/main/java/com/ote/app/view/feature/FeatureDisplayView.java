package com.ote.app.view.feature;

import com.ote.app.Mode;
import com.ote.app.view.AbstractDisplayView;
import com.ote.app.view.AbstractView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 19/12/2015.
 */
public final class FeatureDisplayView extends AbstractDisplayView<Pane> implements IFeatureView {

    private Collection<Text> title = new ArrayList<>(2);

    private Collection<Text> description = new ArrayList<>(10);

    @FXML
    @Override
    public void initialize() {

        FeaturePresenter.getInstance().register(this);

        super.initialize();
    }

    @Override
    protected void fill(Pane root){
        root.getChildren().addAll(this.title.stream().collect(Collectors.toList()));
        root.getChildren().addAll(this.description.stream().collect(Collectors.toList()));
    }

    @FXML
    public void edit(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            FeaturePresenter.getInstance().modeProperty().set(Mode.EDIT);
        }
    }

    @Override
    public String getTitle() {
        return this.title.stream().map(text -> text.getText()).collect(Collectors.joining(" "));
    }

    @Override
    public void setTitle(String title) {

        this.title.clear();

        Text featureName = new Text("Feature: ");
        featureName.getStyleClass().add("name");
        this.title.add(featureName);

        Text featureTitle = new Text(title);
        featureTitle.getStyleClass().add("title");
        this.title.add(featureTitle);
    }

    @Override
    public Collection<String> getDescription() {
        return this.description.stream().map(text -> text.getText()).collect(Collectors.toList());
    }

    @Override
    public void setDescription(Collection<String> description) {

        this.description.clear();

        description.forEach(line -> {
            Text newLine = new Text("\r\n");
            newLine.getStyleClass().add("description");
            this.description.add(newLine);
            Text featureDescription = new Text(line);
            featureDescription.getStyleClass().add(line.startsWith("#") ? "comment" : "description");
            this.description.add(featureDescription);
        });
    }
}
