package com.example.shilh.splash.model;
//史鹂鸿

import android.media.Image;
import android.provider.ContactsContract;

import java.util.Date;

public class Folder {
    private String name;
    private int imageId;
    private String date;

    public Folder(){}

    public Folder(int imageId, String name, String data) {
        this.imageId = imageId;
        this.name = name;
        this.date = data;
    }

    public int getImage() {
        return imageId;
    }

    public void setImage(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
