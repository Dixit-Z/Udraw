package com.trinta.bruno.udraw.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.trinta.bruno.udraw.R;
import com.trinta.bruno.udraw.adaptaters.ImagesFromVideosAdaptater;
import com.trinta.bruno.udraw.content.ImageContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 03/01/2017.
 * This activity allows users to show the different images from one video. All images are listed but they can only draw drawables images.
 */

public class ImagesFromVideo extends Activity {

    private List<ImageContent> imagesList;
    private ListView horizontalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_scroll_view);
        horizontalListView = (ListView) this.findViewById(R.id.horizontalList);
        View header = (View) this.getLayoutInflater().inflate(R.layout.listview_header_row, null);

        imagesList = new ArrayList<>();
        imagesList.add(new ImageContent("titre1", R.drawable.applogo));
        imagesList.add(new ImageContent("titre2", R.drawable.applogo));

        horizontalListView.addHeaderView(header);
        ImagesFromVideosAdaptater imagesAdaptater = new ImagesFromVideosAdaptater(this, R.layout.list_images_from_video, imagesList);
        horizontalListView.setAdapter(imagesAdaptater);
    }
}
