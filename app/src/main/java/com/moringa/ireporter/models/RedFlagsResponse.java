package com.moringa.ireporter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class RedFlagsResponse {
    @SerializedName("redflags")
    @Expose
    List<RedFlag> redFlags = null;

    //Default Cosntructor
    public RedFlagsResponse() {
    }

    public RedFlagsResponse(List<RedFlag> redFlags) {
        super();
        this.redFlags = redFlags;
    }

    public List<RedFlag> getRedFlags() {
        return redFlags;
    }

    public void setRedFlags(List<RedFlag> redFlags) {
        this.redFlags = redFlags;
    }
}
