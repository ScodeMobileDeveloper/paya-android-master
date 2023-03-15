package com.paya.paragon.api.propertyLocation;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PropertyLocationData {
    @SerializedName("emirate") @Expose private String emirate;
    @SerializedName("latitude") @Expose private String latitude;
    @SerializedName("longitude") @Expose private String longitude;
    @SerializedName("communityName") @Expose private String communityName;
    @SerializedName("communityID") @Expose private String communityID;
    @SerializedName("country") @Expose private String country;
    @SerializedName("subCommunity") @Expose private ArrayList<SubCommunity> subCommunity;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getEmirate() {
        return emirate;
    }

    public void setEmirate(String emirate) {
        this.emirate = emirate;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public ArrayList<SubCommunity> getSubCommunity() {
        return subCommunity;
    }

    public void setSubCommunity(ArrayList<SubCommunity> subCommunity) {
        this.subCommunity = subCommunity;
    }
}
