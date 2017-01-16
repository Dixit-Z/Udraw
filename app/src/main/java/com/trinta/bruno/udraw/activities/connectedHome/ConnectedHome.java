package com.trinta.bruno.udraw.activities.connectedHome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;

import com.trinta.bruno.udraw.MenusButtonsInterface;
import com.trinta.bruno.udraw.R;
import com.trinta.bruno.udraw.activities.ProfileScreen;
import com.trinta.bruno.udraw.activities.SearchPage;
import com.trinta.bruno.udraw.activities.VideoRecorder;
import com.trinta.bruno.udraw.activities.connectedHome.fragments.DrawableVideos;
import com.trinta.bruno.udraw.activities.connectedHome.fragments.DrawnVideos;

/**
 * Created by Bruno on 26/09/2016.
 */

public class ConnectedHome extends FragmentActivity implements MenusButtonsInterface {

    //nom des onglets context.getstring(R.string.*);
    final String tabDrawableVideos = "Vidéos récentes";
    final String tabResultsVideos = "Fil de vidéos";
    private FragmentTabHost ntabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected_home);

        //Récupération des onglets.
        ntabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        ntabHost.setup(this, getSupportFragmentManager(), R.layout.connected_home);

        //Onglet hostant les vidéos dessinables.
        TabHost.TabSpec spec = ntabHost.newTabSpec(tabDrawableVideos);
        spec.setIndicator(tabDrawableVideos);
        ntabHost.addTab(spec, DrawableVideos.class, null);

        //Onglet hostant les vidéos résultantes.
        spec = ntabHost.newTabSpec(tabResultsVideos);
        spec.setIndicator(tabResultsVideos);
        ntabHost.addTab(spec, DrawnVideos.class, null);
    }

    @Override
    public void goToHome(View v) {
        return;
    }

    @Override
    public void goToSearch(View v) {
        Intent goSearch = new Intent(ConnectedHome.this, SearchPage.class);
        startActivity(goSearch);
    }

    @Override
    public void goToCamera(View v) {
        Intent goCam = new Intent(ConnectedHome.this, VideoRecorder.class);
        startActivity(goCam);
    }

    @Override
    public void goToFollows(View v) {
        return;
    }

    @Override
    public void goToProfile(View v) {
        Intent goProfile = new Intent(ConnectedHome.this, ProfileScreen.class);
        startActivity(goProfile);
    }
}
