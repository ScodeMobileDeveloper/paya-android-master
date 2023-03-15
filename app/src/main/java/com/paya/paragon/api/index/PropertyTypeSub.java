package com.paya.paragon.api.index;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyTypeSub {
    @SerializedName("propertyTypeID") @Expose private String propertyTypeID;
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;
    @SerializedName("propertyTypeIcon") @Expose private String propertyTypeIcon;

    public String getPropertyTypeIcon() {
        return propertyTypeIcon;
    }

    public void setPropertyTypeIcon(String propertyTypeIcon) {
        this.propertyTypeIcon = propertyTypeIcon;
    }

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }
}
