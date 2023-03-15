package com.paya.paragon.api.editPostRequirement;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EditPostedRequirementData {
    @SerializedName("reqID") @Expose private String reqID;
    @SerializedName("languageID") @Expose private String languageID;
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("propertyTypeID") @Expose private String propertyTypeID;
    @SerializedName("reqPurpose") @Expose private String reqPurpose;
    @SerializedName("reqName") @Expose private String reqName;
    @SerializedName("reqEmail") @Expose private String reqEmail;
    @SerializedName("reqPhone") @Expose private String reqPhone;
    @SerializedName("reqLocalityIDs") @Expose private String reqLocalityIDs;
    @SerializedName("reqBedrooms") @Expose private String reqBedrooms;
    @SerializedName("reqMinPrice") @Expose private String reqMinPrice;
    @SerializedName("reqMaxPrice") @Expose private String reqMaxPrice;
    @SerializedName("reqStatus") @Expose private String reqStatus;
    @SerializedName("reqPostedDate") @Expose private String reqPostedDate;
    @SerializedName("reqKey") @Expose private String reqKey;
    @SerializedName("countryCode") @Expose private String countryCode;
    @SerializedName("locations") @Expose private String locations;

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public String getReqPurpose() {
        return reqPurpose;
    }

    public void setReqPurpose(String reqPurpose) {
        this.reqPurpose = reqPurpose;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getReqEmail() {
        return reqEmail;
    }

    public void setReqEmail(String reqEmail) {
        this.reqEmail = reqEmail;
    }

    public String getReqPhone() {
        return reqPhone;
    }

    public void setReqPhone(String reqPhone) {
        this.reqPhone = reqPhone;
    }

    public String getReqLocalityIDs() {
        return reqLocalityIDs;
    }

    public void setReqLocalityIDs(String reqLocalityIDs) {
        this.reqLocalityIDs = reqLocalityIDs;
    }

    public String getReqBedrooms() {
        return reqBedrooms;
    }

    public void setReqBedrooms(String reqBedrooms) {
        this.reqBedrooms = reqBedrooms;
    }

    public String getReqMinPrice() {
        return reqMinPrice;
    }

    public void setReqMinPrice(String reqMinPrice) {
        this.reqMinPrice = reqMinPrice;
    }

    public String getReqMaxPrice() {
        return reqMaxPrice;
    }

    public void setReqMaxPrice(String reqMaxPrice) {
        this.reqMaxPrice = reqMaxPrice;
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getReqPostedDate() {
        return reqPostedDate;
    }

    public void setReqPostedDate(String reqPostedDate) {
        this.reqPostedDate = reqPostedDate;
    }

    public String getReqKey() {
        return reqKey;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }
}
