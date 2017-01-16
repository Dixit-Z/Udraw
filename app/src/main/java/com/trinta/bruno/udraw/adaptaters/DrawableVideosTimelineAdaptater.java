package com.trinta.bruno.udraw.adaptaters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.trinta.bruno.Holders.PostVideoHolder;
import com.trinta.bruno.udraw.R;
import com.trinta.bruno.udraw.activities.ImagesFromVideo;
import com.trinta.bruno.udraw.content.VideoContent;
import com.trinta.bruno.udraw.helpers.InfoOnDeviceHelper;

import org.jcodec.common.model.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 29/11/2016.
 */

public class DrawableVideosTimelineAdaptater extends ArrayAdapter<VideoContent> {

    Context context;
    int layoutResourceId;
    PostVideoHolder holder;
    List<VideoContent> listOfData = new ArrayList<>();
    Point screenSizeActivity;

    public DrawableVideosTimelineAdaptater(Context context, int resource, List<VideoContent> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.listOfData = objects;
        this.context = context;
        screenSizeActivity = InfoOnDeviceHelper.getScreenSizeInPixels(this.context);
    }

    @Override
    public
    @NonNull
    View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        holder = new PostVideoHolder();

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder.setPostTitle((TextView)row.findViewById(R.id.titleRawVideo));
            holder.setPostVideoContent((VideoView) row.findViewById(R.id.videoDrawable));
            holder.setPencilToDrawn((ImageView) row.findViewById(R.id.penDrawing));
            getItem(position).setHolder(holder);
            row.setTag(holder);
        } else {
            holder = (PostVideoHolder) row.getTag();
        }

        VideoContent videoContent = listOfData.get(position);
        holder.getPostTitle().setText(videoContent.getTitle());
        holder.setPostTitle((TextView)row.findViewById(R.id.titleRawVideo));
        holder.getPostVideoContent().setLayoutParams(new LinearLayout.LayoutParams(screenSizeActivity.getX()-5, screenSizeActivity.getY()-5));
        holder.getPostVideoContent().setBackground(AppCompatResources.getDrawable(context, R.drawable.playbackground));
        holder.getPencilToDrawn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToImages = new Intent(context, ImagesFromVideo.class);
                context.startActivity(goToImages);
            }
        }
        );

        return row;
    }
}
