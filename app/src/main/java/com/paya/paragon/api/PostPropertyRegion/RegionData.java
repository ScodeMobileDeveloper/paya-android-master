package com.paya.paragon.api.PostPropertyRegion;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegionData {
    @SerializedName("regionID") @Expose private String regionID;
    @SerializedName("locationName") @Expose private String locationName;

    public String getRegionID() {
        return regionID;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
