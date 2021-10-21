package com.moringa.ireporter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InterventionResponse {
    @SerializedName("intervention")
    @Expose
    private List<Intervention> interventions = null;

    //Default Cosntructor
    public InterventionResponse() {
    }

    public InterventionResponse(List<Intervention> interventions) {
        super();
        this.interventions = interventions;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }
}
