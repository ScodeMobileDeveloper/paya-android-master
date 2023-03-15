package com.paya.paragon.api.requirementPropertyType;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class RequirementPropertyTypeData {
    @SerializedName("propertyTypeID")  @Expose private String propertyTypeID;
    @SerializedName("propertyTypeName")  @Expose private String propertyTypeName;
    @SerializedName("propertyTypeParentID")  @Expose private String propertyTypeParentID;
    @SerializedName("propertyTypeIcon")  @Expose private String propertyTypeIcon;
    @SerializedName("subCategory")  @Expose private ArrayList<SubPropertyTypeData> subCategory;

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

    public String getPropertyTypeParentID() {
        return propertyTypeParentID;
    }

    public void setPropertyTypeParentID(String propertyTypeParentID) {
        this.propertyTypeParentID = propertyTypeParentID;
    }

    public String getPropertyTypeIcon() {
        return propertyTypeIcon;
    }

    public void setPropertyTypeIcon(String propertyTypeIcon) {
        this.propertyTypeIcon = propertyTypeIcon;
    }

    public ArrayList<SubPropertyTypeData> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(ArrayList<SubPropertyTypeData> subCategory) {
        this.subCategory = subCategory;
    }
}
