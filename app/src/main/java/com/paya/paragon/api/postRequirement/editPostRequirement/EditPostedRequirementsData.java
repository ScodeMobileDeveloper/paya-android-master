package com.paya.paragon.api.postRequirement.editPostRequirement;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.PostPropertyRegion.RegionData;
import com.paya.paragon.api.RequirementAttributeListing.RequirementAllAttributes;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class EditPostedRequirementsData {
    @SerializedName("reqID") @Expose private String reqID;
    @SerializedName("propertyTypeID") @Expose private String propertyTypeID;
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;
    @SerializedName("reqPurpose") @Expose private String reqPurpose;
    @SerializedName("reqName") @Expose private String reqName;
    @SerializedName("reqEmail") @Expose private String reqEmail;
    @SerializedName("reqPhone") @Expose private String reqPhone;
    @SerializedName("reqLocalityIDs") @Expose private String reqLocalityIDs;
    @SerializedName("reqBedrooms") @Expose private String reqBedrooms;
    @SerializedName("reqMinPrice") @Expose private String reqMinPrice;
    @SerializedName("reqMaxPrice") @Expose private String reqMaxPrice;
    @SerializedName("reqStatus") @Expose private String reqStatus;
    @SerializedName("otpVerified") @Expose private String otpVerified;
    @SerializedName("reqKey") @Expose private String reqKey;
    @SerializedName("countryCode") @Expose private String countryCode;
    @SerializedName("reqCityID") @Expose private String reqCityID;
    @SerializedName("stateID") @Expose private String stateID;
    @SerializedName("countryID") @Expose private String countryID;
    @SerializedName("propertyMainTypeID") @Expose private String propertyMainTypeID;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("countryName") @Expose private String countryName;
    @SerializedName("attributeArray") @Expose private ArrayList<RequirementAllAttributes> attributeArray;
    @SerializedName("cityLocName") @Expose private ArrayList<RegionData> cityLocName;

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public ArrayList<RegionData> getCityLocName() {
        return cityLocName;
    }

    public void setCityLocName(ArrayList<RegionData> cityLocName) {
        this.cityLocName = cityLocName;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
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

    public String getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(String otpVerified) {
        this.otpVerified = otpVerified;
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

    public String getReqCityID() {
        return reqCityID;
    }

    public void setReqCityID(String reqCityID) {
        this.reqCityID = reqCityID;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getPropertyMainTypeID() {
        return propertyMainTypeID;
    }

    public void setPropertyMainTypeID(String propertyMainTypeID) {
        this.propertyMainTypeID = propertyMainTypeID;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public ArrayList<RequirementAllAttributes> getAttributeArray() {
        return attributeArray;
    }

    public void setAttributeArray(ArrayList<RequirementAllAttributes> attributeArray) {
        this.attributeArray = attributeArray;
    }
}
