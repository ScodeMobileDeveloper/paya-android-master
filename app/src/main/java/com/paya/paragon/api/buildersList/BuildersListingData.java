package com.paya.paragon.api.buildersList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BuildersListingData {
    @SerializedName("builderList")  @Expose private ArrayList<BuildersList> buildersList;
    @SerializedName("imageURL")  @Expose private String imageURL;
    @SerializedName("totalCount")  @Expose private String totalCount;

    public ArrayList<BuildersList> getBuildersList() {
        return buildersList;
    }

    public void setBuildersList(ArrayList<BuildersList> buildersList) {
        this.buildersList = buildersList;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
