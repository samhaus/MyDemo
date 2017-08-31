package com.example.administrator.demo.VRPlayerDemo;

import java.io.Serializable;

/**
 * Created by lsh on 05/07/2017.
 */

public class VRVideo implements Serializable{
    private String title;
    private String path;
    private long duration;
    private long dateModified;
    private String img;
    private String fileLength;
    private String durationShow;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public String getDurationShow() {
        return durationShow;
    }

    public void setDurationShow(String durationShow) {
        this.durationShow = durationShow;
    }

}
