package com.paya.paragon.api.propertyDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyDetailsData {
    @SerializedName("propertyDetails")  @Expose private PropertyDetailsModel property;
    @SerializedName("totalviews")  @Expose private String totalViews;

    public PropertyDetailsModel getProperty() {
        return property;
    }

    public void setProperty(PropertyDetailsModel property) {
        this.property = property;
    }

    public String getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(String totalViews) {
        this.totalViews = totalViews;
    }
}
