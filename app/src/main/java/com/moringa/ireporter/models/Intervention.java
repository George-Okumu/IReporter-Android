package com.moringa.ireporter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Intervention {
    @SerializedName("intervention_image")
    @Expose
    String imageUrl;
    @SerializedName("subject")
    @Expose
    String subject;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("user")
    @Expose
    String user;
    @SerializedName("location")
    @Expose
    String location;

    // Default constructor
    public Intervention() {
    }

    public Intervention(String imageUrl,String subject, String description, String location) {
        super();
        this.subject = subject;
        this.description = description;
        this.location = location;

        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
