package com.paya.paragon.api.index;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PropertyTypeMain {
    @SerializedName("propertyTypeID") @Expose private String propertyTypeID;
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;
    @SerializedName("subCategory") @Expose private ArrayList<PropertyTypeSub> subCategory;

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

    public ArrayList<PropertyTypeSub> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(ArrayList<PropertyTypeSub> subCategory) {
        this.subCategory = subCategory;
    }
}
