package com.ote.app.view.feature;

import com.ote.app.Mode;
import com.ote.app.view.AbstractEditView;
import javafx.scene.layout.Pane;

import java.util.Collection;

/**
 * Created by Olivier on 17/12/2015.
 */
public class FeatureEditViewMock extends AbstractEditView<Pane> implements IFeatureEditView {

    private String title;

    private Collection<String> description;

    public FeatureEditViewMock() {

        FeaturePresenter.getInstance().register(this);

        super.initialize();
    }

    @Override
    public Mode getMode() {
        return Mode.EDIT;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Collection<String> getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(Collection<String> description) {
        this.description = description;
    }
}