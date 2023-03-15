package com.paya.paragon.api.myPropertyNameList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PropertyNamesData {
    @SerializedName("propertyID") @Expose private String propertyID;
    @SerializedName("propertyName") @Expose private String propertyName;

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
