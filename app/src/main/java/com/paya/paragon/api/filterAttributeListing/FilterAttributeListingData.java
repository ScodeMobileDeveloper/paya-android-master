package com.paya.paragon.api.filterAttributeListing;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class FilterAttributeListingData {
    @SerializedName("allAttributes") @Expose private ArrayList<AllAttributesData> allAttributes;

    public ArrayList<AllAttributesData> getAllAttributes() {
        return allAttributes;
    }

    public void setAllAttributes(ArrayList<AllAttributesData> allAttributes) {
        this.allAttributes = allAttributes;
    }

}
