package com.paya.paragon.api.postProperty.post;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class LoadSavedDeedData {
    @SerializedName("details") @Expose private ArrayList<SavedDeedInfo> details;

    public ArrayList<SavedDeedInfo> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<SavedDeedInfo> details) {
        this.details = details;
    }
}


