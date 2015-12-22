package com.ote.app.view.feature2.display;

/**
 * Created by Olivier on 22/12/2015.
 */
public class FeatureDisplayPresenter {

    private IFeatureDisplayView view;

    public FeatureDisplayPresenter(IFeatureDisplayView view){
        this.view = view;
        this.initialize();
    }

    private void initialize(){

        /**
         * TODO CF FeatureView / FeaturePresenter Plus haut niveau, qui pilote quelle vue (display ou edit) afficher
         */

    }
}
