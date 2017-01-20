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
import com.trinta.bruno.udraw.adaptaters.DrawnVideosTimelineAdaptater;
import com.trinta.bruno.udraw.content.VideoContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 26/09/2016.
 * Méthode permettant de retourner la vue représentant le fragment des vidéos résultats.
 */

public class DrawnVideos extends Fragment {

    private ListView listViewVideos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //On retourne la vue de l'onglet.
        //Vérifier si on ne passe pas plusieurs fois ici
        View V = inflater.inflate(R.layout.fragment_result_videos, container, false);
        //Null to container
        V.inflate(getActivity(), R.layout.fragment_menu, null);

        List<VideoContent> listUntreated = new ArrayList<>();
        listUntreated.add(new VideoContent("Vidéo déssinée", R.raw.videodessinee));
        listUntreated.add(new VideoContent("Vidéo raw", R.raw.videovierge));


        //Appelle de l'adaptateur avec la liste des vidéos déssinées
        DrawnVideosTimelineAdaptater videoTimeLineAdaptater = new DrawnVideosTimelineAdaptater(getActivity(), R.layout.listview_video_drawn, listUntreated);

        listViewVideos = (ListView) V.findViewById(R.id.listViewDrawnVideos);
        listViewVideos.setAdapter(videoTimeLineAdaptater);

        listViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoContent videoContent = (VideoContent) listViewVideos.getAdapter().getItem(position);
                if (videoContent.getHolder().getPostVideoContent().isPlaying()) {
                    videoContent.getHolder().getPostVideoContent().pause();
                    videoContent.getHolder().getPostVideoContent().seekTo(0);
                    videoContent.getHolder().getPostVideoContent().setBackgroundResource(R.drawable.playbackground);
                } else {
                    videoContent.getHolder().getPostVideoContent().start();
                    videoContent.getHolder().getPostVideoContent().setBackground(null);
                }
            }
        });

        return V;
    }
}
