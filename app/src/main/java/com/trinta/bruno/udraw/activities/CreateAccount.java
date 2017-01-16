package com.trinta.bruno.udraw.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trinta.bruno.udraw.helpers.CheckFieldsHelper;
import com.trinta.bruno.udraw.R;

/**
 * Created by Bruno on 04/10/2016.
 * Cette classe représente l'activité de la création d'un compte pour l'application UDraw.
 */

public class CreateAccount extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        Button createAccount = (Button) findViewById(R.id.confirmCreation);

        EditText pwd = (EditText) findViewById(R.id.password);
        EditText confirmPwd = (EditText) findViewById(R.id.confirmPassword);
        EditText identifiant = (EditText) findViewById(R.id.identifiantConnexion);

        final String stringPwd = pwd.toString();
        final String stringConfirmPwd = confirmPwd.toString();
        final String stringIdentifiant = identifiant.toString();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!CheckFieldsHelper.checkEmptyField(stringPwd, stringConfirmPwd, stringIdentifiant)) {
                    Toast.makeText(CreateAccount.this, R.string.errorOnEmptyFields, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
