package com.paya.paragon.api.mySavedSearches;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MySavedSearchesData {

    @SerializedName("savedSearchLists") @Expose private ArrayList<SavedSearchInfo> savedSearchLists;
    @SerializedName("totalCount") @Expose private Integer totalCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<SavedSearchInfo> getSavedSearchLists() {
        return savedSearchLists;
    }

    public void setSavedSearchLists(ArrayList<SavedSearchInfo> savedSearchLists) {
        this.savedSearchLists = savedSearchLists;
    }
}
