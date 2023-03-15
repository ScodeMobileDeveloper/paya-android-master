package com.paya.paragon.api.projectDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AttributesModel {
    @SerializedName("attrName") @Expose private String attrName;
    @SerializedName("attributeID") @Expose private String attributeID;
    @SerializedName("attrDetValue") @Expose private String attrDetValue;
    @SerializedName("attributeKey") @Expose private String attributeKey;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(String attributeID) {
        this.attributeID = attributeID;
    }

    public String getAttrDetValue() {
        return attrDetValue;
    }

    public void setAttrDetValue(String attrDetValue) {
        this.attrDetValue = attrDetValue;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }
}
