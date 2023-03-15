package com.paya.paragon.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProjectGalleryModel {
    @SerializedName("specialListingID") @Expose private String specialListingID;
    @SerializedName("specialListingGroupName") @Expose private String specialListingGroupName;
    @SerializedName("specialListingCreatedDate") @Expose private String specialListingCreatedDate;
    @SerializedName("ProjectGalleryLists") @Expose private ArrayList<ProjectGalleryListModel> ProjectGalleryLists;

    public String getSpecialListingID() {
        return specialListingID;
    }

    public void setSpecialListingID(String specialListingID) {
        this.specialListingID = specialListingID;
    }

    public String getSpecialListingGroupName() {
        return specialListingGroupName;
    }

    public void setSpecialListingGroupName(String specialListingGroupName) {
        this.specialListingGroupName = specialListingGroupName;
    }

    public String getSpecialListingCreatedDate() {
        return specialListingCreatedDate;
    }

    public void setSpecialListingCreatedDate(String specialListingCreatedDate) {
        this.specialListingCreatedDate = specialListingCreatedDate;
    }

    public ArrayList<ProjectGalleryListModel> getProjectGalleryLists() {
        return ProjectGalleryLists;
    }

    public void setProjectGalleryLists(ArrayList<ProjectGalleryListModel> projectGalleryLists) {
        ProjectGalleryLists = projectGalleryLists;
    }
}
