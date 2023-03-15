package com.paya.paragon.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SpecificationInfo {
    @SerializedName("attributeID") @Expose private String attributeID;
    @SerializedName("attrOptName") @Expose private String attrOptName;
    private String attrID;
    private String attrGroupName;
    private String propertyTypeID;

    public SpecificationInfo(String attrOptName, String attributeID){
        this.attributeID = attributeID;
        this.attrOptName = attrOptName;
    }

    public SpecificationInfo(){
    }

    public String getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(String attributeID) {
        this.attributeID = attributeID;
    }

    public String getAttrOptName() {
        return attrOptName;
    }

    public void setAttrOptName(String attrOptName) {
        this.attrOptName = attrOptName;
    }

    public String getAttrID() {
        return attrID;
    }

    public void setAttrID(String attrID) {
        this.attrID = attrID;
    }

    public String getAttrGroupName() {
        return attrGroupName;
    }

    public void setAttrGroupName(String attrGroupName) {
        this.attrGroupName = attrGroupName;
    }

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }
}
