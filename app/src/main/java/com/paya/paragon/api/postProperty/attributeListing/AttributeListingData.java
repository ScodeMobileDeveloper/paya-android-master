package com.paya.paragon.api.postProperty.attributeListing;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class AttributeListingData implements Serializable {
    @SerializedName("allAttributes") @Expose private ArrayList<AllAttributesData> allAttributes;
    @SerializedName("Amenities") @Expose private ArrayList<AmenitiesModel> Amenities;

    public ArrayList<AllAttributesData> getAllAttributes() {
        return allAttributes;
    }

    public void setAllAttributes(ArrayList<AllAttributesData> allAttributes) {
        this.allAttributes = allAttributes;
    }

    public ArrayList<AmenitiesModel> getAmenities() {
        return Amenities;
    }

    public void setAmenities(ArrayList<AmenitiesModel> amenities) {
        Amenities = amenities;
    }
}
