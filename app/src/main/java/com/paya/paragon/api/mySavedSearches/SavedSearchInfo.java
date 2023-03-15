package com.paya.paragon.api.mySavedSearches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SavedSearchInfo {
    @SerializedName("searchID") @Expose private String searchID;
    @SerializedName("searchTitle") @Expose private String searchTitle;
    @SerializedName("searchStatus") @Expose private String searchStatus;
    @SerializedName("searchDate") @Expose private String searchDate;
    @SerializedName("matchCount") @Expose private String matchCount;
    @SerializedName("searchUrl") @Expose private String searchUrl;
    @SerializedName("searchMinPriceValue") @Expose private String searchMinPriceValue;
    @SerializedName("searchMaxPriceValue") @Expose private String searchMaxPriceValue;
    @SerializedName("searchParameters") @Expose private SearchParameters searchParameters;
    @SerializedName("saveSearchParameter") @Expose private SavedSearchParameter saveSearchParameter;

    public String getSearchID() {
        return searchID;
    }

    public void setSearchID(String searchID) {
        this.searchID = searchID;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    public SearchParameters getSearchParameters() {
        return searchParameters;
    }

    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(String matchCount) {
        this.matchCount = matchCount;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public SavedSearchParameter getSaveSearchParameter() {
        return saveSearchParameter;
    }

    public void setSaveSearchParameter(SavedSearchParameter saveSearchParameter) {
        this.saveSearchParameter = saveSearchParameter;
    }

    public String getSearchMinPriceValue() {
        return searchMinPriceValue;
    }

    public void setSearchMinPriceValue(String searchMinPriceValue) {
        this.searchMinPriceValue = searchMinPriceValue;
    }

    public String getSearchMaxPriceValue() {
        return searchMaxPriceValue;
    }

    public void setSearchMaxPriceValue(String searchMaxPriceValue) {
        this.searchMaxPriceValue = searchMaxPriceValue;
    }
}
