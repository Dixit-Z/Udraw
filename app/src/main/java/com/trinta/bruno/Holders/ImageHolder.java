package com.trinta.bruno.Holders;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Bruno on 03/01/2017.
 * Holder pour les images d'une vidéo.
 */

public class ImageHolder implements genericHolder {

    ImageView image;
    TextView  postTitle;

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    @Override
    public TextView getPostTitle() {
        return this.postTitle;
    }

    @Override
    public void setPostTitle(TextView postTitle) {
        this.postTitle = postTitle;
    }
}
