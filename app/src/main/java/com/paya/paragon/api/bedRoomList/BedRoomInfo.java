package com.paya.paragon.api.bedRoomList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BedRoomInfo {
    @SerializedName("attributeID") @Expose private String attributeID;
    @SerializedName("attrOptionID") @Expose private String attrOptionID;

    @SerializedName("attrOptName") @Expose private String attrOptName;

    @SerializedName("optionNewName") @Expose private String optionNewName;
    private boolean selected = false;

    public String getOptionNewName() {
        return optionNewName;
    }

    public void setOptionNewName(String optionNewName) {
        this.optionNewName = optionNewName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

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

    public String getAttrOptionID() {
        return attrOptionID;
    }

    public void setAttrOptionID(String attrOptionID) {
        this.attrOptionID = attrOptionID;
    }
}
