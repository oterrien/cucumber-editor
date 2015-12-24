package com.ote.app.view;

import com.ote.app.command.MultiConsumer;

/**
 * Created by Olivier on 24/12/2015.
 */
public abstract class AbstractView<P extends IPresenter<M, ? extends IView<M>>, M> implements IView<M> {

    private MultiConsumer<Void> validateCommand = new MultiConsumer<>();

    private MultiConsumer<Void> cancelCommand = new MultiConsumer<>();

    private P presenter;

    protected void initialize() {

        this.presenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public MultiConsumer<Void> getValidateCommand() {
        return this.validateCommand;
    }

    @Override
    public MultiConsumer<Void> getCancelCommand() {
        return this.cancelCommand;
    }

    protected P getPresenter() {
        return presenter;
    }

    @Override
    public M getModel() {
        return this.presenter.getModel();
    }

    @Override
    public void setModel(M param) {
        this.presenter.setModel(param);
    }
}
