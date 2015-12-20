package com.ote.app.view.old;

import com.ote.app.Mode;
import com.ote.app.model.Feature;

import java.util.function.Consumer;

/**
 * Created by Olivier on 17/12/2015.
 */
public interface IFeatureViewOld {

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    Mode getMode();

    void setMode(Mode mode);

    void setFeature(Feature feature);

    void addOnSetFeatureHandler(Consumer<Feature> handler);

    void validate();

    void addOnValidateEditionHandler(Runnable handler);

    void cancel();

    void addOnCancelEditionHandler(Runnable handler);


    /**
     * La vue a pour objectif la mise à jour du modele au moment de la validation
     *
     * Et ne rien faire au moment du cancel
     *
     * Le fait qu'on switch de mode ne change rien
     */

}
