package com.paya.paragon.api.BuyProjects;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class BuyProjectsListData {
    @SerializedName("searchProjectCount") @Expose private String searchProjectCount;
    @SerializedName("imageURL") @Expose private String imageURL;
    @SerializedName("siteUrl") @Expose private String siteUrl;
    @SerializedName("propertyLists")  @Expose private List<BuyProjectsModel> projectLists;

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSearchProjectCount() {
        return searchProjectCount;
    }

    public void setSearchProjectCount(String searchProjectCount) {
        this.searchProjectCount = searchProjectCount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<BuyProjectsModel> getProjectLists() {
        return projectLists;
    }

    public void setProjectLists(List<BuyProjectsModel> projectLists) {
        this.projectLists = projectLists;
    }
}
