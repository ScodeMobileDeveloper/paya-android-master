package com.paya.paragon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BathRoomInfo {
    @SerializedName("attributeID") @Expose private String attributeID;
    @SerializedName("attrOptName") @Expose private String attrOptName;
    private String attrID;

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
}
