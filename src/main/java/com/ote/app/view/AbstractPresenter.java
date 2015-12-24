package com.ote.app.view;

import com.ote.app.model.FeatureFormatter;
import com.ote.app.model.FeatureParser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by Olivier on 24/12/2015.
 */
public abstract class AbstractPresenter<M, V extends IView<M>> implements IPresenter<M, V> {

    private ObjectProperty<M> model = new SimpleObjectProperty<>();

    private V view;

    @Override
    public M getModel() {
        return model.get();
    }

    @Override
    public ObjectProperty<M> modelProperty() {
        return model;
    }

    @Override
    public void setModel(M model) {
        this.model.set(model);
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    protected AbstractPresenter(V view) {
        this.view = view;
        initialize();
    }

    protected void initialize(){

        // On model change, fill view with model's new value
        this.modelProperty().addListener((observable, oldValue, newValue) -> fillView(newValue));

        // On view's validation, update model
        this.getView().getValidateCommand().addHandler(aVoid -> fillModel());

        // On view's cancellation, fill view with model's value
        this.getView().getCancelCommand().addHandler(aVoid1 -> fillView(this.getModel()));
    }

    protected abstract void fillView(M model);

    protected abstract  void fillModel();

}
