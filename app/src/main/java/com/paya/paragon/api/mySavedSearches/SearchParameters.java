package com.paya.paragon.api.mySavedSearches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SearchParameters {
    @SerializedName("languageID") @Expose private String languageID;
    @SerializedName("languageKey") @Expose private String languageKey;
    @SerializedName("searchCountryID") @Expose private String searchCountryID;
    @SerializedName("searchCountryKey") @Expose private String searchCountryKey;
    @SerializedName("searchRegion") @Expose private String searchRegion;
    @SerializedName("searchPropertyPurpose") @Expose private String searchPropertyPurpose;
    @SerializedName("searchMinPrice") @Expose private String searchMinPrice;
    @SerializedName("searchMaxPrice") @Expose private String searchMaxPrice;
    @SerializedName("searchPropertyTypeID") @Expose private String searchPropertyTypeID;
    @SerializedName("searchAttributesStr") @Expose private String searchAttributesStr;
    @SerializedName("searchType") @Expose private String searchType;
    @SerializedName("searchCityIDs") @Expose private String searchCityIDs;
    @SerializedName("searchPropertyBedRoom") @Expose private String searchPropertyBedRoom;
    @SerializedName("sortBy") @Expose private String sortBy;
    @SerializedName("priceRange") @Expose private String priceRange;

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getLanguageKey() {
        return languageKey;
    }

    public void setLanguageKey(String languageKey) {
        this.languageKey = languageKey;
    }

    public String getSearchCountryID() {
        return searchCountryID;
    }

    public void setSearchCountryID(String searchCountryID) {
        this.searchCountryID = searchCountryID;
    }

    public String getSearchCountryKey() {
        return searchCountryKey;
    }

    public void setSearchCountryKey(String searchCountryKey) {
        this.searchCountryKey = searchCountryKey;
    }

    public String getSearchRegion() {
        return searchRegion;
    }

    public void setSearchRegion(String searchRegion) {
        this.searchRegion = searchRegion;
    }

    public String getSearchPropertyPurpose() {
        return searchPropertyPurpose;
    }

    public void setSearchPropertyPurpose(String searchPropertyPurpose) {
        this.searchPropertyPurpose = searchPropertyPurpose;
    }

    public String getSearchMinPrice() {
        return searchMinPrice;
    }

    public void setSearchMinPrice(String searchMinPrice) {
        this.searchMinPrice = searchMinPrice;
    }

    public String getSearchMaxPrice() {
        return searchMaxPrice;
    }

    public void setSearchMaxPrice(String searchMaxPrice) {
        this.searchMaxPrice = searchMaxPrice;
    }

    public String getSearchPropertyTypeID() {
        return searchPropertyTypeID;
    }

    public void setSearchPropertyTypeID(String searchPropertyTypeID) {
        this.searchPropertyTypeID = searchPropertyTypeID;
    }

    public String getSearchAttributesStr() {
        return searchAttributesStr;
    }

    public void setSearchAttributesStr(String searchAttributesStr) {
        this.searchAttributesStr = searchAttributesStr;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchCityIDs() {
        return searchCityIDs;
    }

    public void setSearchCityIDs(String searchCityIDs) {
        this.searchCityIDs = searchCityIDs;
    }

    public String getSearchPropertyBedRoom() {
        return searchPropertyBedRoom;
    }

    public void setSearchPropertyBedRoom(String searchPropertyBedRoom) {
        this.searchPropertyBedRoom = searchPropertyBedRoom;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }
}
