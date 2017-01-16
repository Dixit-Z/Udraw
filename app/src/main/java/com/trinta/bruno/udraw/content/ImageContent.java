package com.trinta.bruno.udraw.content;

import com.trinta.bruno.Holders.ImageHolder;

/**
 * Created by Bruno on 03/01/2017.
 *
 */

public class ImageContent implements genericContent {

    private ImageHolder holder;

    private String title;

    private Integer idRawImage;

    public ImageContent(String title, Integer idRawImage)
    {
        this.title = title;
        this.idRawImage = idRawImage;
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageHolder getHolder() {
        return holder;
    }

    public void setHolder(ImageHolder holder) {
        this.holder = holder;
    }
}
