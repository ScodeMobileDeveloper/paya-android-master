package com.paya.paragon.api.localExpertDetials;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LocalExpertInfo {

    @SerializedName("userID") @Expose private String businessID;
    @SerializedName("userCompanyName") @Expose private String businessName;
    @SerializedName("userYearOfInception") @Expose private String estYear;
    @SerializedName("countryName") @Expose private String countryName;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("userAddress1") @Expose private String locAddress1;
    @SerializedName("userAddress2") @Expose private String locAddress2;
    @SerializedName("userAboutMe") @Expose private String businessService;
    @SerializedName("userSkills") @Expose private String businessProfile;
    @SerializedName("businessCDesignation") @Expose private String businessCDesignation;
    @SerializedName("userFirstName") @Expose private String businessCFirstName;
    @SerializedName("userProfilePicture") @Expose private String businessCImage;
    @SerializedName("userCompanyLogo") @Expose private String businessLogo;
    @SerializedName("userCategoryNames") @Expose private String userCategoryNames;
    @SerializedName("userCompanyUrlKey") @Expose private String userCompanyUrlKey;
    @SerializedName("userTwitter") @Expose private String userTwitter;
    @SerializedName("userLinkedin") @Expose private String userLinkedin;
    @SerializedName("userLastName") @Expose private String userLastName;
    @SerializedName("userEmail") @Expose private String userEmail;
    @SerializedName("userPhone") @Expose private String userPhone;
   // @SerializedName("userCategoryNames") @Expose private ArrayList<LocalExpertCategoryData> category;
   @SerializedName("resPropCatImgArr") @Expose private ArrayList<LocalExpertGalleryImgsModel> galleryImgs;
    @SerializedName("imageUrl") @Expose private String imageUrl;
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getBusinessID() {
        return businessID;
    }

    public ArrayList<LocalExpertGalleryImgsModel> getGalleryImgs() {
        return galleryImgs;
    }

    public void setGalleryImgs(ArrayList<LocalExpertGalleryImgsModel> galleryImgs) {
        this.galleryImgs = galleryImgs;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEstYear() {
        return estYear;
    }

    public void setEstYear(String estYear) {
        this.estYear = estYear;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getBusinessService() {
        return businessService;
    }

    public void setBusinessService(String businessService) {
        this.businessService = businessService;
    }

    public String getBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(String businessProfile) {
        this.businessProfile = businessProfile;
    }

    public String getBusinessCDesignation() {
        return businessCDesignation;
    }

    public void setBusinessCDesignation(String businessCDesignation) {
        this.businessCDesignation = businessCDesignation;
    }

    public String getBusinessCFirstName() {
        return businessCFirstName;
    }

    public void setBusinessCFirstName(String businessCFirstName) {
        this.businessCFirstName = businessCFirstName;
    }

    public String getBusinessCImage() {
        return businessCImage;
    }

    public void setBusinessCImage(String businessCImage) {
        this.businessCImage = businessCImage;
    }

    public String getBusinessLogo() {
        return businessLogo;
    }

    public void setBusinessLogo(String businessLogo) {
        this.businessLogo = businessLogo;
    }


    public String getUserCategoryNames() {
        return userCategoryNames;
    }

    public void setUserCategoryNames(String userCategoryNames) {
        this.userCategoryNames = userCategoryNames;
    }

    public String getUserCompanyUrlKey() {
        return userCompanyUrlKey;
    }

    public void setUserCompanyUrlKey(String userCompanyUrlKey) {
        this.userCompanyUrlKey = userCompanyUrlKey;
    }

    public String getUserTwitter() {
        return userTwitter;
    }

    public void setUserTwitter(String userTwitter) {
        this.userTwitter = userTwitter;
    }

    public String getUserLinkedin() {
        return userLinkedin;
    }

    public void setUserLinkedin(String userLinkedin) {
        this.userLinkedin = userLinkedin;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
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
}
