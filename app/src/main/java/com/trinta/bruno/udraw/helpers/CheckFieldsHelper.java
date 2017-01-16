package com.trinta.bruno.udraw.helpers;

import android.app.Activity;
import android.widget.Toast;

import com.trinta.bruno.udraw.R;

/**
 * Created by Bruno on 04/10/2016.
 * Cette classe stock toutes les procédures de vérification des champs des différentes activités.
 */

public class CheckFieldsHelper {

    /**
     * Fonction prenant en paramètre plusieurs String.
     * Renvoi un booléen, vrai si les champs ne sont pas vides, faux si au moins un champ est vide.
     *
     * @param stringList
     * @return
     */
    public static Boolean checkEmptyField(String... stringList) {
        for (String uniqueString : stringList) {
            if (uniqueString.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Méthode prenant X string en paramètres et renvoyant un booléen si au moins
     * un champ vide est trouvé affiche un message dans l'écran de l'activité.
     * TRUE : Aucun champ vide
     * FALSE : Au moins un champ vide
     *
     * @param activity
     * @param stringList
     * @return
     */
    public static Boolean checkEmptyFieldWithMessage(final Activity activity, String... stringList)
    {
        for (String uniqueString : stringList) {
            if (uniqueString.isEmpty()) {
                Toast.makeText(activity, R.string.errorOnEmptyFields, Toast.LENGTH_LONG);
                return false;
            }
        }
        return true;
    }

    public static Boolean checkPasswordsComplexityEquality(final String password, final String passwordConfirmation)
    {
        if(!password.equals(passwordConfirmation))
        {
            //error
        }

        return Boolean.TRUE;
    }

}
