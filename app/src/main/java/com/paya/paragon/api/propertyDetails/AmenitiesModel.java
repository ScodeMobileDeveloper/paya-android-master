package com.paya.paragon.api.propertyDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class AmenitiesModel implements Serializable {
    @SerializedName("attrOptionID") @Expose private String attrOptionID;
    @SerializedName("attrOptName") @Expose private String attrOptName;
    @SerializedName("attributeID") @Expose private String attributeID;
    @SerializedName("attrOptionImage") @Expose private String attrOptionImage;

    public String getAttrOptionImage() {
        return attrOptionImage;
    }

    public void setAttrOptionImage(String attrOptionImage) {
        this.attrOptionImage = attrOptionImage;
    }

    public String getAttrOptionID() {
        return attrOptionID;
    }

    public void setAttrOptionID(String attrOptionID) {
        this.attrOptionID = attrOptionID;
    }

    public String getAttrOptName() {
        return attrOptName;
    }

    public void setAttrOptName(String attrOptName) {
        this.attrOptName = attrOptName;
    }

    public String getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(String attributeID) {
        this.attributeID = attributeID;
    }
}
