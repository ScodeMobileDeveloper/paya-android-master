package com.paya.paragon.api.projectDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FloorPlans {

    @SerializedName("propertyImageID")
    @Expose
    private String propertyImageID;
    @SerializedName("propertyImageName")
    @Expose
    private String propertyImageName;

    public String getPropertyImageID() {
        return propertyImageID;
    }

    public void setPropertyImageID(String propertyImageID) {
        this.propertyImageID = propertyImageID;
    }

    public String getPropertyImageName() {
        return propertyImageName;
    }

    public void setPropertyImageName(String propertyImageName) {
        this.propertyImageName = propertyImageName;
    }
}
