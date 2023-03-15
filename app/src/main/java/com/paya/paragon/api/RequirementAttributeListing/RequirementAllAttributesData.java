package com.paya.paragon.api.RequirementAttributeListing;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RequirementAllAttributesData {

    @SerializedName("attrName") @Expose private String attrName;
    @SerializedName("attrID") @Expose private String attrID;
    @SerializedName("attrGroupName") @Expose private String attrGroupName;
    @SerializedName("propertyAttrSelectedOptionID") @Expose private String propertyAttrSelectedOptionID;
    @SerializedName("attrOptName") @Expose private String attrOptName;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
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

    public String getPropertyAttrSelectedOptionID() {
        return propertyAttrSelectedOptionID;
    }

    public void setPropertyAttrSelectedOptionID(String propertyAttrSelectedOptionID) {
        this.propertyAttrSelectedOptionID = propertyAttrSelectedOptionID;
    }

    public String getAttrOptName() {
        return attrOptName;
    }

    public void setAttrOptName(String attrOptName) {
        this.attrOptName = attrOptName;
    }
}
