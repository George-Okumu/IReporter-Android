package com.moringa.ireporter.models;

import com.moringa.ireporter.R;

import org.parceler.Parcel;
@Parcel
public class RedFlag {
     String subject;
     String description;
     String location;
     int imageUrl;
    // Default constructor
    public RedFlag() {
    }

    public RedFlag(String subject, String description, String location) {
        this.subject = subject;
        this.description = description;
        this.location = location;
        this.imageUrl = R.drawable.corruption1;
    }

    public RedFlag(String subject, String description, String location, int image) {
        this.subject = subject;
        this.description = description;
        this.location = location;
        this.imageUrl = image;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
