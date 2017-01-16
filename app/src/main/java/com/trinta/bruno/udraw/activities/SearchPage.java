package com.trinta.bruno.udraw.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.trinta.bruno.udraw.activities.connectedHome.ConnectedHome;
import com.trinta.bruno.udraw.enumerations.CategoriesVideo;
import com.trinta.bruno.udraw.MenusButtonsInterface;
import com.trinta.bruno.udraw.R;

/**
 * Created by Bruno on 28/09/2016.
 */

public class SearchPage extends Activity implements MenusButtonsInterface {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        ScrollView generalLayout = (ScrollView) findViewById(R.id.resultSearch);
        createSections(generalLayout);
    }

    /**
     * Cette méthode créé les différentes sections dans la page de recherche sur l'application.
     */
    private void createSections(ScrollView v) {
        LinearLayout boxOfLayout = new LinearLayout(this);
        boxOfLayout.setOrientation(LinearLayout.VERTICAL);
        for (CategoriesVideo oneCategory : CategoriesVideo.values()) {
            LinearLayout layoutOfCategory = new LinearLayout(this);
            layoutOfCategory.setOrientation(LinearLayout.VERTICAL);
            TextView title = new TextView(this);
            //TODO TraductionEnumHelper
            title.setText(oneCategory.name());
            title.setTextColor(Color.WHITE);
            layoutOfCategory.addView(title);
            boxOfLayout.addView(layoutOfCategory);
        }
        v.addView(boxOfLayout);
    }

    @Override
    public void goToHome(View v) {
        Intent goHome = new Intent(SearchPage.this, ConnectedHome.class);
        startActivity(goHome);
    }

    @Override
    public void goToSearch(View v) {
        return;
    }

    @Override
    public void goToCamera(View v) {
        Intent goCam = new Intent(SearchPage.this, VideoRecorder.class);
        startActivity(goCam);
    }

    @Override
    public void goToFollows(View v) {
        return;
    }

    @Override
    public void goToProfile(View v) {
        Intent goProfile = new Intent(SearchPage.this, ProfileScreen.class);
        startActivity(goProfile);
    }
}
