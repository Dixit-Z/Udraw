package com.trinta.bruno.udraw.content;

import com.trinta.bruno.Holders.PostVideoHolder;

import java.math.BigInteger;

/**
 * Created by Bruno on 29/11/2016.
 */

public class VideoContent {

    private PostVideoHolder holder;

    private String title;

    private BigInteger videoContent;

    private Integer preview;

    public VideoContent(String ptitle, Integer dataImg) {
        this.title = ptitle;
        this.preview = dataImg;
    }

    public VideoContent() {
        this.title = "Default title";
    }

    public VideoContent(String ptitle, BigInteger data) {
        this.title = ptitle;
        this.videoContent = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getVideoContent() {
        return videoContent;
    }

    public void setVideoContent(BigInteger videoContent) {
        this.videoContent = videoContent;
    }

    public PostVideoHolder getHolder() {
        return holder;
    }

    public void setHolder(PostVideoHolder holder) {
        this.holder = holder;
    }

    public Integer getPreview() {
        return preview;
    }

    public void setPreview(Integer preview) {
        this.preview = preview;
    }
}
