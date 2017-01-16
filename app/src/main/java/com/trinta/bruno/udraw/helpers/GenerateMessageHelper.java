package com.trinta.bruno.udraw.helpers;

import android.app.Activity;
import android.widget.Toast;

import com.trinta.bruno.udraw.R;

/**
 * Created by Bruno on 22/11/2016.
 *
 * Cette classe permet de générer des messages pour les différentes activités.
 */

public class GenerateMessageHelper {

    /**
     * Cette méthode permet de générer un message d'erreur lors d'une erreur de connexion.
     * Cette erreur survient si l'utilisateur est en mode dev mais utilise un pseudo inexistant
     * dans l'application ou en mode prod où le login ou mot de passe est erroné.
     * @param activity
     */
    public static void generateErrorConnectionMessage(final Activity activity)
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, R.string.errorOnConnexion, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Méthode permettant d'afficher un message informatif si le mode dev est
     * activé lors de la connexion à l'application.
     * @param activity
     * @param modeDev
     */
    public static void generateMessageForDevelopers(final Activity activity, final Boolean modeDev)
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(modeDev) {
                    Toast.makeText(activity, R.string.devMode, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
