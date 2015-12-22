package com.ote.app.view.feature2.display;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;

/**
 * Created by Olivier on 17/12/2015.
 */
public interface IFeatureDisplayView {

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    void initialize();

    Mode getMode();

    void show();

    MultiConsumer<Void> getShowCommand();

    void hide();

    MultiConsumer<Void> getHideCommand();


}
