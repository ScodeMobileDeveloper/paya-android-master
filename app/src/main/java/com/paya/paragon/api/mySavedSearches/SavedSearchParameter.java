package com.paya.paragon.api.mySavedSearches;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unused")
public class SavedSearchParameter {
    @SerializedName("searchLocation") @Expose private String searchLocation;
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;
    @SerializedName("searchPropertyPurpose") @Expose private String searchPropertyPurpose;
    @SerializedName("minPrice") @Expose private String minPrice;
    @SerializedName("maxPrice") @Expose private String maxPrice;
    @SerializedName("searchRegion") @Expose private String searchRegion;
    @SerializedName("attrDetails") @Expose private HashMap<String, String> attrDetails;
    @SerializedName("stateDetails") @Expose private ArrayList<StateDetails> stateDetails;
    @SerializedName("cityDetails") @Expose private ArrayList<CityDetails> cityDetails;
    @SerializedName("locationDetails") @Expose private ArrayList<LocationDetails> locationDetails;


    public String getSearchLocation() {
        return searchLocation;
    }

    public void setSearchLocation(String searchLocation) {
        this.searchLocation = searchLocation;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getSearchPropertyPurpose() {
        return searchPropertyPurpose;
    }

    public void setSearchPropertyPurpose(String searchPropertyPurpose) {
        this.searchPropertyPurpose = searchPropertyPurpose;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSearchRegion() {
        return searchRegion;
    }

    public void setSearchRegion(String searchRegion) {
        this.searchRegion = searchRegion;
    }

    public HashMap<String, String> getAttrDetails() {
        return attrDetails;
    }

    public void setAttrDetails(HashMap<String, String> attrDetails) {
        this.attrDetails = attrDetails;
    }

    public ArrayList<StateDetails> getStateDetails() {
        return stateDetails;
    }

    public void setStateDetails(ArrayList<StateDetails> stateDetails) {
        this.stateDetails = stateDetails;
    }

    public ArrayList<CityDetails> getCityDetails() {
        return cityDetails;
    }

    public void setCityDetails(ArrayList<CityDetails> cityDetails) {
        this.cityDetails = cityDetails;
    }

    public ArrayList<LocationDetails> getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(ArrayList<LocationDetails> locationDetails) {
        this.locationDetails = locationDetails;
    }
}
