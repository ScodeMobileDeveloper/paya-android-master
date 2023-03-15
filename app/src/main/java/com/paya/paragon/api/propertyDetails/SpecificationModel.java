package com.paya.paragon.api.propertyDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificationModel {
    @SerializedName("attrName") @Expose private String attrName;
    @SerializedName("attrDetValue") @Expose private String attrDetValue;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrDetValue() {
        return attrDetValue;
    }

    public void setAttrDetValue(String attrDetValue) {
        this.attrDetValue = attrDetValue;
    }
}
