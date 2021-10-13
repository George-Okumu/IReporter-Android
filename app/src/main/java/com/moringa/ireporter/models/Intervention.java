package com.moringa.ireporter.models;

public class Intervention {
    private String title;
    private String description;
    private String user;
    private String intervention_image;
    private String intervention_video;
    private String intervention_location;

    public Intervention(String title, String description, String user, String intervention_image, String intervention_video, String intervention_location) {
        this.title = title;
        this.description = description;
        this.intervention_image = intervention_image;
        this.user = user;
        this.intervention_video = intervention_video;
        this.intervention_location = intervention_location;
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

    public String getIntervention_image() {
        return intervention_image;
    }

    public void setIntervention_image(String intervention_image) {
        this.intervention_image = intervention_image;
    }

    public String getIntervention_video() {
        return intervention_video;
    }

    public void setIntervention_video(String intervention_video) {
        this.intervention_video = intervention_video;
    }

    public String getIntervention_location() {
        return intervention_location;
    }

    public void setIntervention_location(String intervention_location) {
        this.intervention_location = intervention_location;
    }
}
