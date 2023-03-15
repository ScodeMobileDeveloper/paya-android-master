package com.paya.paragon.api.RequirementAttributeListing;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class RequirementAttributeListingData {
    @SerializedName("allAttributes") @Expose private ArrayList<RequirementAllAttributes> allAttributes;

    public ArrayList<RequirementAllAttributes> getAllAttributes() {
        return allAttributes;
    }

    public void setAllAttributes(ArrayList<RequirementAllAttributes> allAttributes) {
        this.allAttributes = allAttributes;
    }
}
