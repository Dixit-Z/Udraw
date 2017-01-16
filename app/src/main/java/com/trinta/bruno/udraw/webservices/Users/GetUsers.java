package com.trinta.bruno.udraw.webservices.Users;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Bruno on 07/11/2016.
 */
//Classe deprecated car elle ne respecte pas les standards de l'application.
@Deprecated
public class GetUsers {

    private static final String TAG = "GetUsers";
    //Activité appelante de la classe GetUsers
    private Activity activity;

    public GetUsers(Activity pactivity) {
        this.activity = pactivity;
    }

    private final OkHttpClient client = new OkHttpClient();

    private Boolean isValidUser;
    private String mailUser;

    //Déclaration de l'objet de communication REST
    //On build la requête Get vers notre client
    Request myGetRequest = new Request.Builder()
            .url("https://udrawapp.000webhostapp.com/select_users.php")
            .build();
    //$7vWGyMGfH%1c42LdaGx

    public Boolean ifExistsUser(String pmailUser) {
        this.mailUser = pmailUser;
        client.newCall(myGetRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String text = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    isValidUser = isUserInBase(mailUser, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: ifExistsUser", e);
                }

                //on récupère la réponse dans un thread différent
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Faire ce qu'il faut avec text
                        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return isValidUser;
    }

    private boolean isUserInBase(String mailUser, JSONObject JsonData) {
        Boolean userValid = Boolean.FALSE;
        try {
            JSONArray users = JsonData.getJSONArray("users");
            for(int i = 0; i < users.length(); i++)
            {
                JSONObject singleUser = users.getJSONObject(i);
                if(singleUser.getString("pseudo").equals(mailUser))
                {
                    userValid = Boolean.TRUE;
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "isUserInBase: Could not create JSON array users", e);
            e.printStackTrace();
        }
        return userValid;
    }

/*    public Boolean verifyUserPseudo() {
        Boolean isUserValid = Boolean.FALSE;
        //Fabrication de l'URL.
        HttpUrl.Builder urlBuilder = HttpUrl.parse(verificationPseudoUrl).newBuilder();
        urlBuilder.addQueryParameter("pseudo", this.userPseudo);
        //Création de la requête avec le paramètre
        this.myGetRequest = new Request.Builder().url(urlBuilder.toString()).build();

        try {
            Response response = client.newCall(myGetRequest).execute();
            final String textResponse = response.body().string();
            try {
                final JSONObject jsonObject = new JSONObject(textResponse);
                String jsonResponse = jsonObject.getString("success");
                Toast.makeText(activity, jsonResponse, Toast.LENGTH_LONG).show();
                Boolean result ;
                if ("1".equals(jsonResponse)) {
                    result = Boolean.TRUE;
                } else {
                    result = Boolean.FALSE;
                }
                onSucces(result);
            } catch (JSONException e) {
                Log.e(TAG, "verifyUserPseudo: Couldn't instantiate JSONObject", e);
            }
        } catch (IOException e) {
            Log.e(TAG, "verifyUserPseudo: IOException during httpRequest", e);
        }
        return isUserValid;
    }*/
}
