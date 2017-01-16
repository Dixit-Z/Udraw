package com.trinta.bruno.udraw.adaptaters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.trinta.bruno.Holders.PostVideoHolder;
import com.trinta.bruno.udraw.R;
import com.trinta.bruno.udraw.content.VideoContent;
import com.trinta.bruno.udraw.helpers.InfoOnDeviceHelper;

import org.jcodec.common.model.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 29/11/2016.
 *
 * Adapteur de la time line des vidéos déssinées dans l'accueil connecté de l'application.
 */

public class DrawnVideosTimelineAdaptater extends ArrayAdapter<VideoContent> {

    Context context;
    int layoutResourceId;
    List<VideoContent> listOfData = new ArrayList<>();
    PostVideoHolder holder = null;
    Point screenSizeActivity;

    public DrawnVideosTimelineAdaptater(Context context, int resource, List<VideoContent> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.listOfData = objects;
        this.context = context;
        this.screenSizeActivity = InfoOnDeviceHelper.getScreenSizeInPixels(this.context);
    }

    @Override
    public @NonNull View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        holder = new PostVideoHolder();
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder.setPostVideoContent((VideoView)row.findViewById(R.id.videoDrawn));
            holder.setPostTitle((TextView)row.findViewById(R.id.videoTitle));
            getItem(position).setHolder(holder);
            row.setTag(holder);
        }
        else
        {
            holder = (PostVideoHolder) row.getTag();
        }

        VideoContent videoContent = listOfData.get(position);
        holder.getPostTitle().setText(videoContent.getTitle());
        holder.getPostVideoContent().setVideoURI(Uri.parse("http://www.fieldandrurallife.tv/videos/Benltey%20Mulsanne.mp4"));
        holder.getPostVideoContent().setLayoutParams(new LinearLayout.LayoutParams(screenSizeActivity.getX()-5, screenSizeActivity.getY()-5));
        holder.getPostVideoContent().setBackground(AppCompatResources.getDrawable(context, R.drawable.playbackground));

        return row;
    }
}
