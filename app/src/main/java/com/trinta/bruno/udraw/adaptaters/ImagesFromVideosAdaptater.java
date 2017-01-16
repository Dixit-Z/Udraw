package com.trinta.bruno.udraw.adaptaters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.VideoView;

import com.trinta.bruno.Holders.ImageHolder;
import com.trinta.bruno.udraw.R;
import com.trinta.bruno.udraw.content.ImageContent;
import com.trinta.bruno.udraw.helpers.InfoOnDeviceHelper;

import org.jcodec.common.model.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 03/01/2017.
 * Adaptateur des images extraites des vidéos
 */

public class ImagesFromVideosAdaptater extends ArrayAdapter<ImageContent> {
    private final int layoutResourceId;
    Context context;
    List<ImageContent> listOfData = new ArrayList<>();
    Point screenSizeActivity;
    ImageHolder holder;

    public ImagesFromVideosAdaptater(Context context, int resource, List<ImageContent> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.listOfData = objects;
        this.context = context;
        this.screenSizeActivity = InfoOnDeviceHelper.getScreenSizeInPixels(this.context);
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        holder = new ImageHolder();
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder.setImage((ImageView) row.findViewById(R.id.imageFromVideo));
            getItem(position).setHolder(holder);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder) row.getTag();
        }

        ImageContent imageContent = listOfData.get(position);
        holder.setImage(imageContent.getHolder().getImage());
        return row;
    }
}
