package com.ote.app.view;

import com.ote.app.Mode;
import com.ote.app.command.MultiConsumer;

import java.util.Collection;

/**
 * Created by Olivier on 17/12/2015.
 */
public interface IFeatureView {

    /**
     * La vue a pour objectif la mise à jour du modele au moment de la validation
     * <p>
     * Et ne rien faire au moment du cancel
     * <p>
     * Le fait qu'on switch de mode ne change rien
     */

    Mode getMode();

    String getTitle();

    void setTitle(String title);

    Collection<String> getDescription();

    void setDescription(Collection<String> description);

    void show();

    MultiConsumer<Void> getShowCommand();

    void hide();

    MultiConsumer<Void> getHideCommand();


}
