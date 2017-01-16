package com.trinta.bruno.Holders;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by Bruno on 12/12/2016.
 * Classe holder d'un post d'une vidéo avec un titre.
 */

public class PostVideoHolder implements genericHolder {

    private TextView postTitle;

    private VideoView postVideoContent;

    private ImageView pencilToDrawn;

    public ImageView getPencilToDrawn() {
        return pencilToDrawn;
    }

    public void setPencilToDrawn(ImageView pencilToDrawn) {
        this.pencilToDrawn = pencilToDrawn;
    }

    @Override

    public TextView getPostTitle() {
        return postTitle;
    }

    @Override
    public void setPostTitle(TextView postTitle) {
        this.postTitle = postTitle;
    }

    public VideoView getPostVideoContent() {
        return postVideoContent;
    }

    public void setPostVideoContent(VideoView postVideoContent) {
        this.postVideoContent = postVideoContent;
    }
}
