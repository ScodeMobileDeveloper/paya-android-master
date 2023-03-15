package com.paya.paragon.api.postProperty.post;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SavedDeedInfo {

    @SerializedName("titleDeed") @Expose private String titleDeed;
    @SerializedName("titleDeedID") @Expose private String titleDeedID;

    public String getTitleDeed() {
        return titleDeed;
    }

    public void setTitleDeed(String titleDeed) {
        this.titleDeed = titleDeed;
    }

    public String getTitleDeedID() {
        return titleDeedID;
    }

    public void setTitleDeedID(String titleDeedID) {
        this.titleDeedID = titleDeedID;
    }
}
