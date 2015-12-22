package com.ote.app.view;

import javafx.scene.layout.Pane;

/**
 * Created by Olivier on 22/12/2015.
 */
public abstract class AbstractDisplayView<T extends Pane> extends AbstractView<T> implements IView {

    @Override
    public void initialize() {

        super.initialize();

        // On showing this view, the root pane (TextFlow) is set with title and description content
        getShowCommand().addHandler(none -> {
            this.root.getChildren().clear();
            this.fill(this.root);
        });
    }

    protected abstract void fill(T root);
}
