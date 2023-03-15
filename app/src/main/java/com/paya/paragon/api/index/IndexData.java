package com.paya.paragon.api.index;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IndexData {
    @SerializedName("locations") @Expose private ArrayList<LocationInfo> locations;
    @SerializedName("propertyType") @Expose private ArrayList<PropertyTypeMain> propertyType;
    @SerializedName("minPrice") @Expose private ArrayList<MinimumPrice> minPrice;
    @SerializedName("maxPrice") @Expose private ArrayList<MaximumPrice> maxPrice;

    public ArrayList<LocationInfo> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<LocationInfo> locations) {
        this.locations = locations;
    }

    public ArrayList<PropertyTypeMain> getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(ArrayList<PropertyTypeMain> propertyType) {
        this.propertyType = propertyType;
    }

    public ArrayList<MinimumPrice> getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(ArrayList<MinimumPrice> minPrice) {
        this.minPrice = minPrice;
    }

    public ArrayList<MaximumPrice> getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(ArrayList<MaximumPrice> maxPrice) {
        this.maxPrice = maxPrice;
    }
}
