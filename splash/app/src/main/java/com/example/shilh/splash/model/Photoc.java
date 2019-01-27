package com.example.shilh.splash.model;

import java.util.List;

public class Photoc {
    private String filename;
    private String mtopimagepath;
    private int mimageCounts;
    private List<String> imageurl;


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMtopimagepath() {
        return mtopimagepath;
    }

    public void setMtopimagepath(String mtopimagepath) {
        this.mtopimagepath = mtopimagepath;
    }

    public int getMimageCounts() {
        return mimageCounts;
    }

    public void setMimageCounts(int mimageCounts) {
        this.mimageCounts = mimageCounts;
    }

    public List<String> getImageurl() {
        return imageurl;
    }

    public void setImageurl(List<String> imageurl) {
        this.imageurl = imageurl;
    }
}
