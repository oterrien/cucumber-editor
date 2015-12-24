package com.ote.app.view;

import javafx.beans.property.ObjectProperty;

/**
 * Created by Olivier on 24/12/2015.
 */
public interface IPresenter<M, V extends IView<M>> {

    M getModel();

    ObjectProperty<M> modelProperty();

    void setModel(M model);

    V getView();

    void setView(V view);
}
