package com.paya.paragon.api.upgradePlanApi;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpgradePlanData {
    @SerializedName("imgPath") @Expose private String imgPath;

    @SerializedName("billingFirstName") @Expose private String billingFirstName;
    @SerializedName("billingLastName") @Expose private String billingLastName;
    @SerializedName("userEmail") @Expose private String userEmail;
    @SerializedName("userPhone") @Expose private String userPhone;
    @SerializedName("billingAddrs1") @Expose private String billingAddrs1;
    @SerializedName("billingAddrs2") @Expose private String billingAddrs2;
    @SerializedName("countryID") @Expose private String countryID;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("stateID") @Expose private String stateID;
    @SerializedName("cityID") @Expose private String cityID;
    @SerializedName("userZip") @Expose private String userZip;
    @SerializedName("availRewardPoint") @Expose private String availRewardPoint;
    @SerializedName("tax_applicable") @Expose private String tax_applicable;
    @SerializedName("Tax_calc") @Expose private String Tax_calc;
    @SerializedName("result_Tax_calc") @Expose private String result_Tax_calc;
    @SerializedName("pointUsable") @Expose private String pointUsable;
    @SerializedName("currntRewAmnt") @Expose private String currntRewAmnt;
    @SerializedName("updatedRewPoint") @Expose private String updatedRewPoint;
    @SerializedName("countryCode") @Expose private String countryCode;

    @SerializedName("orderTotalAmount") @Expose private String orderTotalAmount;
    @SerializedName("resCountry") @Expose private ArrayList<UpgradePlanCountryData> resCountry;
    @SerializedName("paymentSettingsGroupID") @Expose private ArrayList<PaymentSettings> paymentSettings;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public ArrayList<PaymentSettings> getPaymentSettings() {
        return paymentSettings;
    }

    public void setPaymentSettings(ArrayList<PaymentSettings> paymentSettings) {
        this.paymentSettings = paymentSettings;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getBillingFirstName() {
        return billingFirstName;
    }

    public void setBillingFirstName(String billingFirstName) {
        this.billingFirstName = billingFirstName;
    }

    public String getBillingLastName() {
        return billingLastName;
    }

    public void setBillingLastName(String billingLastName) {
        this.billingLastName = billingLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBillingAddrs1() {
        return billingAddrs1;
    }

    public void setBillingAddrs1(String billingAddrs1) {
        this.billingAddrs1 = billingAddrs1;
    }

    public String getBillingAddrs2() {
        return billingAddrs2;
    }

    public void setBillingAddrs2(String billingAddrs2) {
        this.billingAddrs2 = billingAddrs2;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getUserZip() {
        return userZip;
    }

    public void setUserZip(String userZip) {
        this.userZip = userZip;
    }

    public String getAvailRewardPoint() {
        return availRewardPoint;
    }

    public void setAvailRewardPoint(String availRewardPoint) {
        this.availRewardPoint = availRewardPoint;
    }

    public String getTax_applicable() {
        return tax_applicable;
    }

    public void setTax_applicable(String tax_applicable) {
        this.tax_applicable = tax_applicable;
    }

    public String getTax_calc() {
        return Tax_calc;
    }

    public void setTax_calc(String tax_calc) {
        Tax_calc = tax_calc;
    }

    public String getResult_Tax_calc() {
        return result_Tax_calc;
    }

    public void setResult_Tax_calc(String result_Tax_calc) {
        this.result_Tax_calc = result_Tax_calc;
    }

    public String getPointUsable() {
        return pointUsable;
    }

    public void setPointUsable(String pointUsable) {
        this.pointUsable = pointUsable;
    }

    public String getCurrntRewAmnt() {
        return currntRewAmnt;
    }

    public void setCurrntRewAmnt(String currntRewAmnt) {
        this.currntRewAmnt = currntRewAmnt;
    }

    public String getUpdatedRewPoint() {
        return updatedRewPoint;
    }

    public void setUpdatedRewPoint(String updatedRewPoint) {
        this.updatedRewPoint = updatedRewPoint;
    }

    public String getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(String orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public ArrayList<UpgradePlanCountryData> getResCountry() {
        return resCountry;
    }

    public void setResCountry(ArrayList<UpgradePlanCountryData> resCountry) {
        this.resCountry = resCountry;
    }
}
