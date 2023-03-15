package com.paya.paragon.api.localExpertList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LocalExpertListData {
    @SerializedName("userID") @Expose private String businessID;
    @SerializedName("userCompanyLogo") @Expose private String businessLogo;
    @SerializedName("userEmail") @Expose private String businessEmail;
    @SerializedName("userPhone") @Expose private String businessMobile;
    @SerializedName("userCompanyName") @Expose private String businessName;
    @SerializedName("userCategoryNames") @Expose private String dealCategoryName;
    @SerializedName("landmark") @Expose private String landmark;
    @SerializedName("areaName") @Expose private String areaName;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("locAddress1") @Expose private String locAddress1;
    @SerializedName("locAddress2") @Expose private String locAddress2;
    @SerializedName("address") @Expose private String address;
    @SerializedName("reviewCount") @Expose private String ratingCount;
    @SerializedName("netRating") @Expose private String ratingTotal;
    @SerializedName("voucherCount") @Expose private int voucherCount;
    @SerializedName("userUrlKey") @Expose private String userUrlKey;

    public int getVoucherCount() {
        return voucherCount;
    }

    public String getUserUrlKey() {
        return userUrlKey;
    }

    public void setUserUrlKey(String userUrlKey) {
        this.userUrlKey = userUrlKey;
    }

    public void setVoucherCount(int voucherCount) {
        this.voucherCount = voucherCount;
    }

    public String getBusinessLogo() {
        return businessLogo;
    }

    public void setBusinessLogo(String businessLogo) {
        this.businessLogo = businessLogo;
    }

    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessMobile() {
        return businessMobile;
    }

    public void setBusinessMobile(String businessMobile) {
        this.businessMobile = businessMobile;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocAddress1() {
        return locAddress1;
    }

    public void setLocAddress1(String locAddress1) {
        this.locAddress1 = locAddress1;
    }

    public String getLocAddress2() {
        return locAddress2;
    }

    public void setLocAddress2(String locAddress2) {
        this.locAddress2 = locAddress2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getRatingTotal() {
        return ratingTotal;
    }

    public void setRatingTotal(String ratingTotal) {
        this.ratingTotal = ratingTotal;
    }

    public String getDealCategoryName() {
        return dealCategoryName;
    }

    public void setDealCategoryName(String dealCategoryName) {
        this.dealCategoryName = dealCategoryName;
    }
}
