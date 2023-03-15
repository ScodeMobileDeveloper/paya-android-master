package com.paya.paragon.api.bedRoomList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyAttribute {
    @SerializedName("attrName") @Expose private String attrName;
    @SerializedName("attrOptName") @Expose private String attrOptName;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrOptName() {
        return attrOptName;
    }

    public void setAttrOptName(String attrOptName) {
        this.attrOptName = attrOptName;
    }
}
