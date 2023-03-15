package com.paya.paragon.api.postedRequirements;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PostedRequirementsDataModel {
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;
    @SerializedName("reqID") @Expose private String reqID;
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("reqPurpose") @Expose private String reqPurpose;
    @SerializedName("reqName") @Expose private String reqName;
    @SerializedName("reqEmail") @Expose private String reqEmail;
    @SerializedName("reqPhone") @Expose private String reqPhone;
    @SerializedName("reqLocalityIDs") @Expose private String reqLocalityIDs;
    @SerializedName("reqMaxPrice") @Expose private String reqMaxPrice;
    @SerializedName("reqMinPrice") @Expose private String reqMinPrice;
    @SerializedName("reqPostedDate") @Expose private String reqPostedDate;
    @SerializedName("propertyTypeID") @Expose private String propertyTypeID;
    @SerializedName("LocDetails") @Expose private String LocDetails;
    @SerializedName("reqBedrooms") @Expose private String reqBedrooms;

    public String getReqMinPrice() {
        return reqMinPrice;
    }

    public void setReqMinPrice(String reqMinPrice) {
        this.reqMinPrice = reqMinPrice;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getReqMaxPrice() {
        return reqMaxPrice;
    }

    public void setReqMaxPrice(String reqMaxPrice) {
        this.reqMaxPrice = reqMaxPrice;
    }

    public String getReqPostedDate() {
        return reqPostedDate;
    }

    public void setReqPostedDate(String reqPostedDate) {
        this.reqPostedDate = reqPostedDate;
    }

    public String getLocDetails() {
        return LocDetails;
    }

    public void setLocDetails(String locDetails) {
        LocDetails = locDetails;
    }

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }
}
