package com.trinta.bruno.udraw.webservices.Users;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.trinta.bruno.udraw.activities.connectedHome.ConnectedHome;
import com.trinta.bruno.udraw.helpers.GenerateMessageHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Bruno on 21/11/2016.
 */

public class ConnectionValidation {

    private static final String TAG = "ConnectionValidation";
    //Activité appelante de la classe GetUsers
    private final Activity activity;

    private final String userPseudo;
    private final String verificationPseudoUrl = "https://udrawapp.000webhostapp.com/verif_connexion.php";
    private final Boolean isModeDev;

    //Constructeur
    public ConnectionValidation(Activity pactivity, String ppseudo) {
        this.activity = pactivity;
        this.userPseudo = ppseudo;
        this.isModeDev = Boolean.FALSE;
    }

    public ConnectionValidation(Activity pactivity, String ppseudo, Boolean pIsModeDev)
    {
        this.activity = pactivity;
        this.userPseudo = ppseudo;
        this.isModeDev = pIsModeDev;
    }

    //Client okHttp pour effectuer les requêtes.
    private OkHttpClient client = new OkHttpClient();
    private Request myGetRequest;

    public void verificationOnPseudo() {

        //Fabrication de l'URL.
        HttpUrl.Builder urlBuilder = HttpUrl.parse(verificationPseudoUrl).newBuilder();
        urlBuilder.addQueryParameter("pseudo", this.userPseudo);
        //Création de la requête avec le paramètre
        this.myGetRequest = new Request.Builder().url(urlBuilder.toString()).build();

        client.newCall(myGetRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: onFailure okHttp request", e);
            }

            //Réalisation du traitement de la réponse dans le thread en background
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String textResponse = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(textResponse);
                    String jsonResponse = jsonObject.getString("success");
                    Boolean result = Boolean.FALSE;
                    if ("1".equals(jsonResponse)) {
                        result = Boolean.TRUE;
                    }
                    onSuccess(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: ifExistsUser", e);
                }
            }
        });
        return;
    }

    /**
     * Méthode permettant de réaliser l'action après avoir obtenu le résultat
     * de la requête au serveur
     * @param userInSystem
     */
    private void onSuccess(final Boolean userInSystem)
    {
        //On traite les informations dans l'acitivté concernée.
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if((isModeDev && userInSystem) || (!isModeDev && userInSystem))
                {
                    Intent toConnectedHome = new Intent(activity, ConnectedHome.class);
                    toConnectedHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(toConnectedHome);
                }
                else
                {
                    GenerateMessageHelper.generateErrorConnectionMessage(activity);
                }
            }
        });
    }
}
