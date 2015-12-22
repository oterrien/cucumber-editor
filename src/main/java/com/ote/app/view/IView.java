package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;

/**
 * Created by Olivier on 21/12/2015.
 */
public interface IView {

    void initialize();

    Mode getMode();

    void show();

    MultiConsumer<Void> getShowCommand();

    void hide();

    MultiConsumer<Void> getHideCommand();
}
