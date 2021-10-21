package com.moringa.ireporter.models;

public class InterventionRes {
    String intervention_image;
    String subject;
    String description;
    String user;
    String location;

    public String getIntervention_image() {
        return intervention_image;
    }

    public void setIntervention_image(String intervention_image) {
        this.intervention_image = intervention_image;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
