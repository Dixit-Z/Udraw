package com.trinta.bruno.udraw.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.trinta.bruno.udraw.MenusButtonsInterface;
import com.trinta.bruno.udraw.R;
import com.trinta.bruno.udraw.activities.connectedHome.ConnectedHome;

/**
 * Created by Bruno on 03/10/2016.
 */

public class ProfileScreen extends Activity implements MenusButtonsInterface {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);
    }

    @Override
    public void goToHome(View v) {
        Intent goHome = new Intent(ProfileScreen.this, ConnectedHome.class);
        startActivity(goHome);
    }

    @Override
    public void goToSearch(View v) {
        Intent goSearch = new Intent(ProfileScreen.this, SearchPage.class);
        startActivity(goSearch);
    }

    @Override
    public void goToCamera(View v) {
        Intent goCam = new Intent(ProfileScreen.this, VideoRecorder.class);
        startActivity(goCam);
    }

    @Override
    public void goToFollows(View v) {

    }

    @Override
    public void goToProfile(View v) {

    }
}
