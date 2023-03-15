package com.paya.paragon.api.myProperties;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MyPropertiesItem {
    @SerializedName("propertyID")
    private String propertyID;
    @SerializedName("countryName")
    private String countryName;
    @SerializedName("stateName")
    private String stateName;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("currencySymbolLeft")
    private String currencySymbolLeft;
    @SerializedName("currencySymbolRight")
    private String currencySymbolRight;
    @SerializedName("propertyCoverImage")
    private String propertyCoverImage;
    @SerializedName("propertyPurpose")
    private String propertyPurpose;
    @SerializedName("address1")
    private String address1;
    @SerializedName("address2")
    private String address2;
    @SerializedName("propertyLocality")
    private String propertyLocality;
    @SerializedName("propertyAddedDate")
    private String propertyAddedDate;
    @SerializedName("propertyUpdateDate")
    private String propertyUpdateDate;
    @SerializedName("propertyPrice")
    private String propertyPrice;
    @SerializedName("propertyName")
    private String propertyName;
    @SerializedName("propertySqrFeet")
    private String propertySqrFeet;
    @SerializedName("propEnqCount")
    private String propEnqCount;
    @SerializedName("planTitle")
    private String planTitle;
    @SerializedName("planID")
    private String planID;
    @SerializedName("changePlan")
    private String changePlan;
    @SerializedName("premiumEndDate")
    private String premiumEndDate;
    @SerializedName("propertyAreaType")
    private String propertyAreaType;
    @SerializedName("restotalViews")
    private String restotalViews;
    @SerializedName("activePlanList")
    private ArrayList<ActivePlanList> activePlanList;



    @SerializedName("propertyStatus")
    private String propertyStatus;

    @SerializedName("currencyID_1")
    private String currencyID_1;
    @SerializedName("currencyID_5")
    private String currencyID_5;
    @SerializedName("total_price")
    private String total_price;

    @SerializedName("propertyPricePerM2")
    private String propertyPricePerM2;


    public String getPropertyLocality() {
        return propertyLocality;
    }

    public void setPropertyLocality(String propertyLocality) {
        this.propertyLocality = propertyLocality;
    }

    public String getPropertyAreaType() {
        return propertyAreaType;
    }

    public void setPropertyAreaType(String propertyAreaType) {
        this.propertyAreaType = propertyAreaType;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropertyCoverImage() {
        return propertyCoverImage;
    }

    public void setPropertyCoverImage(String propertyCoverImage) {
        this.propertyCoverImage = propertyCoverImage;
    }

    public String getPropertyPurpose() {
        return propertyPurpose;
    }

    public void setPropertyPurpose(String propertyPurpose) {
        this.propertyPurpose = propertyPurpose;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getCurrencySymbolLeft() {
        return currencySymbolLeft;
    }

    public void setCurrencySymbolLeft(String currencySymbolLeft) {
        this.currencySymbolLeft = currencySymbolLeft;
    }

    public String getCurrencySymbolRight() {
        return currencySymbolRight;
    }

    public void setCurrencySymbolRight(String currencySymbolRight) {
        this.currencySymbolRight = currencySymbolRight;
    }

    public String getPropEnqCount() {
        return propEnqCount;
    }

    public void setPropEnqCount(String propEnqCount) {
        this.propEnqCount = propEnqCount;
    }

    public String getPropertyAddedDate() {
        return propertyAddedDate;
    }

    public void setPropertyAddedDate(String propertyAddedDate) {
        this.propertyAddedDate = propertyAddedDate;
    }

    public String getPropertyUpdateDate() {
        return propertyUpdateDate;
    }

    public void setPropertyUpdateDate(String propertyUpdateDate) {
        this.propertyUpdateDate = propertyUpdateDate;
    }

    public String getPropertySqrFeet() {
        return propertySqrFeet;
    }

    public void setPropertySqrFeet(String propertySqrFeet) {
        this.propertySqrFeet = propertySqrFeet;
    }

    public ArrayList<ActivePlanList> getActivePlanList() {
        return activePlanList;
    }

    public void setActivePlanList(ArrayList<ActivePlanList> activePlanList) {
        this.activePlanList = activePlanList;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getChangePlan() {
        return changePlan;
    }

    public void setChangePlan(String changePlan) {
        this.changePlan = changePlan;
    }

    public String getPremiumEndDate() {
        return premiumEndDate;
    }

    public void setPremiumEndDate(String premiumEndDate) {
        this.premiumEndDate = premiumEndDate;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getRestotalViews() {
        return restotalViews;
    }

    public void setRestotalViews(String restotalViews) {
        this.restotalViews = restotalViews;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getCurrencyID_1() {
        return currencyID_1;
    }

    public void setCurrencyID_1(String currencyID_1) {
        this.currencyID_1 = currencyID_1;
    }

    public String getCurrencyID_5() {
        return currencyID_5;
    }

    public void setCurrencyID_5(String currencyID_5) {
        this.currencyID_5 = currencyID_5;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPropertyPricePerM2() {
        return propertyPricePerM2;
    }

    public void setPropertyPricePerM2(String propertyPricePerM2) {
        this.propertyPricePerM2 = propertyPricePerM2;
    }
}
