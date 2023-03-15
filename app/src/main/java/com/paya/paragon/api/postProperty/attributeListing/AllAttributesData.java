package com.paya.paragon.api.postProperty.attributeListing;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class
AllAttributesData implements Serializable{

    @SerializedName("attrName") @Expose private String attrName;
    @SerializedName("selection") @Expose private String selection;
    @SerializedName("attrID") @Expose private String attrID;
    @SerializedName("attrGroupName") @Expose private String attrGroupName;
    @SerializedName("propertyAttrSelectedOption") @Expose private String propertyAttrSelectedOption;
    @SerializedName("propertyAttrSelectedOptionID") @Expose private String getPropertyAttrSelectedOptionID;
    @SerializedName("propertyTypeID") @Expose private String propertyTypeID;
    @SerializedName("attrOptName") @Expose private String attrOptName;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
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
        return getPropertyAttrSelectedOptionID;
    }

    public void setPropertyAttrSelectedOptionID(String getPropertyAttrSelectedOptionID) {
        this.getPropertyAttrSelectedOptionID = getPropertyAttrSelectedOptionID;
    }

    public String getAttrOptName() {
        return attrOptName;
    }

    public void setAttrOptName(String attrOptName) {
        this.attrOptName = attrOptName;
    }

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public String getPropertyAttrSelectedOption() {
        return propertyAttrSelectedOption;
    }

    public void setPropertyAttrSelectedOption(String propertyAttrSelectedOption) {
        this.propertyAttrSelectedOption = propertyAttrSelectedOption;
    }
}
