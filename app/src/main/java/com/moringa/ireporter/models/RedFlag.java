package com.moringa.ireporter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moringa.ireporter.R;

import org.parceler.Parcel;
@Parcel
public class RedFlag {
    @SerializedName("redFlag_image")
    @Expose
    String imageUrl;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("redFlag_video")
    @Expose
    String videoUrl;
    @SerializedName("user")
    @Expose
    String user;
    @SerializedName("redFlag_location")
    @Expose
    String location;

    // Default constructor
    public RedFlag() {
    }

    public RedFlag(String imageUrl,String title, String description, String location) {
        super();
        this.title = title;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
