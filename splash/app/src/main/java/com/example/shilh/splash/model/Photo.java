package com.example.shilh.splash.model;
//slh
public class Photo {
    private String path;
    private String date;
    private long id;
    private String discr;
    private String name;

    public Photo(String path, String date, long id,  String name) {
        this.path = path;
        this.date = date;
        this.id = id;
        this.name = name;
    }

    public Photo(String path, String date) {
        this.path = path;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
