package com.trinta.bruno.udraw.activities;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trinta.bruno.udraw.R;
import com.trinta.bruno.udraw.webservices.Users.ConnectionValidation;
import com.trinta.bruno.udraw.helpers.GenerateMessageHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe launcher de l'application. Possibilité de se connecter ou de créer un nouveau compte utilisateur.
 */

public class MainActivity extends AppCompatActivity {

    Properties properties = new Properties();
    Boolean isModeDev = Boolean.FALSE;
    ConnectionValidation connectionValidation;

    private Boolean isValidUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            AssetManager justeTest = getBaseContext().getAssets();
            InputStream inputAssets = getBaseContext().getAssets().open("app.properties");
            if(inputAssets != null) {
                properties.load(inputAssets);
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, R.string.IOError,Toast.LENGTH_SHORT).show();
            System.console().printf("[ERROR] Lors de l'ouverture du fichier app.properties");
        }

        this.isModeDev = Boolean.valueOf(properties.getProperty("dev_profile", "true"));
        GenerateMessageHelper.generateMessageForDevelopers(MainActivity.this, isModeDev);

        //Récupération du modèle de la page.
        Button connectButton = (Button) findViewById(R.id.connexionButton);
        final EditText login = (EditText) findViewById(R.id.identifiantConnexion);
        final EditText pwd = (EditText) findViewById(R.id.password);

        //Listener sur le bouton de connexion.
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Vérification des champs de connexion
                if (checkEmptyField(login.getText().toString(), pwd.getText().toString())) {
                    connectionValidation = new ConnectionValidation(MainActivity.this, login.getText().toString());
                    connectionValidation.verificationOnPseudo();
                }
            }
        });
    }

    /**
     * Fonction prenant en paramètre plusieurs String.
     * Renvoi un booléen, vrai si les champs ne sont pas vides, faux si au moins un champ est vide.
     * Le système remonte un message d'erreur si un champ vide est trouvé.
     *
     * @param stringList
     * @return
     */
    private Boolean checkEmptyField(String... stringList) {
        for (String uniqueString : stringList) {
            if (uniqueString.isEmpty()) {
                //On remonte un message court qui indique que des champs ne doivent pas être vides.
                Toast.makeText(MainActivity.this, R.string.errorOnEmptyFields, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
