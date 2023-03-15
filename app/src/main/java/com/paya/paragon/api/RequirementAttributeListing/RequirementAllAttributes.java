package com.paya.paragon.api.RequirementAttributeListing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class RequirementAllAttributes {
    @SerializedName("subAttributes") @Expose private ArrayList<AllAttributesData> subAttributes;

    public ArrayList<AllAttributesData> getSubAttributes() {
        return subAttributes;
    }

    public void setSubAttributes(ArrayList<AllAttributesData> subAttributes) {
        this.subAttributes = subAttributes;
    }
}
