package com.paya.paragon.api.propertyList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.model.PropertyModel;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PropertyListData {
    @SerializedName("totalCount") @Expose private String totalCount;
    @SerializedName("imageURL") @Expose private String imageURL;
    @SerializedName("propertyLists")  @Expose private ArrayList<PropertyModel> propertyLists;
    @SerializedName("serializedParameters")  @Expose private ArrayList<SerializedParameters> serializedParameters;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<PropertyModel> getPropertyLists() {
        return propertyLists;
    }

    public void setPropertyLists(ArrayList<PropertyModel> propertyLists) {
        this.propertyLists = propertyLists;
    }

    public ArrayList<SerializedParameters> getSerializedParameters() {
        return serializedParameters;
    }

    public void setSerializedParameters(ArrayList<SerializedParameters> serializedParameters) {
        this.serializedParameters = serializedParameters;
    }
}
