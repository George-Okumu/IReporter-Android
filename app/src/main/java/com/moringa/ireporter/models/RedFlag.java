package com.moringa.ireporter.models;

public class RedFlag {
    private String title;
    private String description;
    private String user;
    private String redflag_image;
    private String redflag_video;
    private String redflag_location;


    public RedFlag(String title, String description, String user, String redflag_image, String redflag_video, String redflag_location) {
        this.title = title;
        this.description = description;
        this.redflag_image = redflag_image;
        this.redflag_video = redflag_image;
        this.redflag_location = redflag_location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRedflag_image() {
        return redflag_image;
    }

    public void setRedflag_image(String redflag_image) {
        this.redflag_image = redflag_image;
    }

    public String getRedflag_video() {
        return redflag_video;
    }

    public void setRedflag_video(String redflag_video) {
        this.redflag_video = redflag_video;
    }

    public String getRedflag_location() {
        return redflag_location;
    }

    public void setRedflag_location(String redflag_location) {
        this.redflag_location = redflag_location;
    }
}
