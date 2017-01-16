package com.trinta.bruno.udraw;

import android.view.View;

/**
 * Interface pour chacun des 5 boutons de la barre de navigation dans l'application.
 * Toutes les activités comportant la barre de navigation doivent implémenter cette interface.
 * Created by Bruno on 28/09/2016.
 */

public interface MenusButtonsInterface {
    /**
     * Méthode à implémenter lors de l'action du clic sur le bouton Home de la barre de navigation.
     */
    public void goToHome(View v);
    /**
     * Méthode à implémenter lors de l'action du clic sur le bouton Search de la barre de navigation.
     */
    public void goToSearch(View v);
    /**
     * Méthode à implémenter lors de l'action du clic sur le bouton Camera de la barre de navigation.
     */
    public void goToCamera(View v);
    /**
     * Méthode à implémenter lors de l'action du clic sur le bouton Follows de la barre de navigation.
     */
    public void goToFollows(View v);
    /**
     * Méthode à implémenter lors de l'action du clic sur le bouton Profile de la barre de navigation.
     */
    public void goToProfile(View v);
}
