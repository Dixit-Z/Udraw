package com.trinta.bruno.udraw.activities.connectedHome.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.trinta.bruno.udraw.R;
import com.trinta.bruno.udraw.adaptaters.DrawableVideosTimelineAdaptater;
import com.trinta.bruno.udraw.content.VideoContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 26/09/2016.
 * <p>
 * Activité comportant la liste des vidéos qui contiennent des images dessi
 */

public class DrawableVideos extends Fragment {

    private ListView listViewVideos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //On retourne la vue de l'onglet.
        View V = inflater.inflate(R.layout.fragment_drawable_videos, container, false);
        V.inflate(getActivity(), R.layout.fragment_menu, null);

        //Récupération des vidéos de manière paginée
        //Alimentation de l'activité avec les vidéos récupérées
        List<VideoContent> listUntreated = new ArrayList<>();
        listUntreated.add(new VideoContent("Image 1", R.drawable.images1));
        listUntreated.add(new VideoContent("Image 2", R.drawable.images2));
        listUntreated.add(new VideoContent("Image 3", R.drawable.images3));
        listUntreated.add(new VideoContent("Image 4", R.drawable.images4));

        //Appel avec les vidéos non traitées (ici images)
        DrawableVideosTimelineAdaptater videoTimeLineAdaptater = new DrawableVideosTimelineAdaptater(getActivity(), R.layout.listview_video_raw, listUntreated);

        View header = (View) getActivity().getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listViewVideos = (ListView) V.findViewById(R.id.listViewDrawableVideos);
        listViewVideos.addHeaderView(header);
        listViewVideos.setAdapter(videoTimeLineAdaptater);
        listViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoContent videoContent = (VideoContent) listViewVideos.getAdapter().getItem(position);
                if (videoContent.getHolder().getPostVideoContent().isPlaying()) {
                    videoContent.getHolder().getPostVideoContent().pause();
                    videoContent.getHolder().getPostVideoContent().seekTo(0);
                    videoContent.getHolder().getPostVideoContent().setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.playbackground));
                } else {
                    videoContent.getHolder().getPostVideoContent().start();
                    videoContent.getHolder().getPostVideoContent().setBackground(null);
                }
            }
        });

        return V;
    }
}
