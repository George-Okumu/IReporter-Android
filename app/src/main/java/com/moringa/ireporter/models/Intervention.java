package com.moringa.ireporter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Intervention {
    @SerializedName("id")
    @Expose
    int id;
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
    @SerializedName("status")
    @Expose
    String status;

    // Default constructor
    public Intervention() {
    }

    public Intervention(int id,String imageUrl,String subject, String description, String location,String status) {
        super();
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.location = location;
        this.status = status;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
