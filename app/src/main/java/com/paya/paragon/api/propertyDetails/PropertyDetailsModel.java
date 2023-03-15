package com.paya.paragon.api.propertyDetails;


import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.buy_properties.BuyPropertiesModel;
import com.paya.paragon.api.postProperty.loadVideo.SavedVideoInfo;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class
 PropertyDetailsModel {
    @SerializedName("userID")
    private String userID;
    @SerializedName("propertyID")
    private String propertyID;
    @SerializedName("propertyKey")
    private String propertyKey;
    @SerializedName("propertyName")
    private String propertyName;
    @SerializedName("projectName")
    private String projectName;
    @SerializedName("projectUserCompanyName")
    private String projectUserCompanyName;
    @SerializedName("possessionStatus")
    private String possessionStatus;
    @SerializedName("propertyAddress1")
    private String propertyAddress1;
    @SerializedName("propertyAddress2")
    private String propertyAddress2;
    @SerializedName("propertyCityName")
    private String propertyCityName;
    @SerializedName("userTypeID")
    private String userTypeID;
    @SerializedName("userCompanyLogo")
    private String userCompanyLogo;
    @SerializedName("userCompanyName")
    private String userCompanyName;
    @SerializedName("propertyAddedDate")
    private String propertyAddedDate;
    @SerializedName("propertyDescription")
    private String propertyDescription;
    @SerializedName("propertyFeatured")
    private String propertyFeatured;
    @SerializedName("propertySoldOutStatus")
    private String propertySoldOutStatus;
    @SerializedName("propertySoldOutDate")
    private String propertySoldOutDate;
    @SerializedName("propertyPrice")
    private String propertyPrice;
    @SerializedName("pricePerSqft")
    private String pricePerSqft;
    @SerializedName("planEnquiryCountBalance")
    private String planEnquiryCountBalance;
    @SerializedName("propertySqrFeet")
    private String propertySqrFeet;
    @SerializedName("pricePerUnit")
    private String pricePerUnit;
    @SerializedName("pricePerUnitValue")
    private String pricePerUnitValue;
    @SerializedName("propertyTypeName")
    private String propertyTypeName;
    @SerializedName("propertyTypeKey")
    private String propertyTypeKey;
    @SerializedName("propertyLat")
    private String propertyLat;
    @SerializedName("propertyLong")
    private String propertyLong;
    @SerializedName("propertyFavourite")
    private Integer propertyFavourite;
    @SerializedName("listAmenities")
    private ArrayList<AmenitiesModel> lstAmenities;
    @SerializedName("propertyAtrributes")
    private ArrayList<SpecificationModel> propertyAtrributes;
    // @SerializedName("monthlyMortgage")private ArrayList<MonthlyMortgage> monthlyMortgage;
    @SerializedName("propertyImages")
    private PropertyImages propertyImages;
    @SerializedName("propertyLink")
    private String propertyLink;
    @SerializedName("propertyStateName")
    private String propertyStateName;
    @SerializedName("propertyStatus")
    private String propertyStatus;
    @SerializedName("propertyCurrentStatus")
    private String propertyCurrentStatus;
    @SerializedName("possessionDate")
    private String possessionDate;
    @SerializedName("propertyBuiltUpArea")
    private String propertyBuiltUpArea;
    @SerializedName("propertyPlotArea")
    private String propertyPlotArea;
    @SerializedName("propertyBathRoom")
    private String propertyBathRoom;
    @SerializedName("areaType")
    private String areaType;
    @SerializedName("propertyBedRoom")
    private String propertyBedRoom;
    @SerializedName("similarProperties")
    private ArrayList<BuyPropertiesModel> similarProperties;
    @SerializedName("propertyLocality")
    private String propertyLocality;
    @SerializedName("propertyLocationName")
    private String propertyLocationName;
    @SerializedName("amenitiesImgPath")
    private String amenitiesImgPath;
    @SerializedName("imageUrl")
    private String imagePath;
    @SerializedName("propertyVideos")
    private ArrayList<SavedVideoInfo> propertyVideos;
    @SerializedName("similerImageUrl")
    private String similerImageUrl;
    @SerializedName("propertyTypeIcon")
    private String propertyTypeIcon;
    @SerializedName("bluePrintimageURL")
    private String bluePrintimageURL;
    @SerializedName("ownerPhone")
    private String ownerPhone;
    @SerializedName("propertyShowPhone")
    private String propertyShowPhone;

    @SerializedName("isOffer")
    private String isOffer;
    @SerializedName("propertyOfferPrice")
    private String propertyOfferPrice;
    @SerializedName("propertyOfferDiscount")
    private String propertyOfferDiscount;
    @SerializedName("propertyOfferEndTime")
    private String propertyOfferEndTime;
    @SerializedName("additionalPhone1")
    private String additionalPhone_1;
    @SerializedName("additionalPhone2")
    private String additionalPhone_2;
    @SerializedName("currencyID_1")
    private String currencyID_1;
    @SerializedName("currencyID_5")
    private String currencyID_5;
    @SerializedName("propertyUnitPriceRange")
    private String propertyUnitPriceRange;
    @SerializedName("total_price")
    private String total_price;
    @SerializedName("propertyPricePerM2")
    private String propertyPricePerM2;
    @SerializedName("countryCodeone")
    private String countryCodeone;
    @SerializedName("countryCodeTwo")
    private String countryCodeTwo;
    @SerializedName("userCountryCode")
    private String userCountryCode;

    public String getUserCountryCode() {
        return userCountryCode;
    }

    public void setUserCountryCode(String userCountryCode) {
        this.userCountryCode = userCountryCode;
    }

    public String getCountryCodeone() {
        return countryCodeone;
    }

    public void setCountryCodeone(String countryCodeone) {
        this.countryCodeone = countryCodeone;
    }

    public String getCountryCodeTwo() {
        return countryCodeTwo;
    }

    public void setCountryCodeTwo(String countryCodeTwo) {
        this.countryCodeTwo = countryCodeTwo;
    }

    public String getPropertyUnitPriceRange() {
        return propertyUnitPriceRange;
    }

    public void setPropertyUnitPriceRange(String propertyUnitPriceRange) {
        this.propertyUnitPriceRange = propertyUnitPriceRange;
    }

    public String getPropertyOfferEndTime() {
        return propertyOfferEndTime;
    }

    public void setPropertyOfferEndTime(String propertyOfferEndTime) {
        this.propertyOfferEndTime = propertyOfferEndTime;
    }

    public String getIsOffer() {
        return isOffer;
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

    public void setIsOffer(String isOffer) {
        this.isOffer = isOffer;
    }

    public String getPropertyOfferPrice() {
        return propertyOfferPrice;
    }

    public void setPropertyOfferPrice(String propertyOfferPrice) {
        this.propertyOfferPrice = propertyOfferPrice;
    }

    public String getPropertyOfferDiscount() {
        return propertyOfferDiscount;
    }

    public void setPropertyOfferDiscount(String propertyOfferDiscount) {
        this.propertyOfferDiscount = propertyOfferDiscount;
    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }

    public String getPropertyCurrentStatus() {
        return propertyCurrentStatus;
    }

    public void setPropertyCurrentStatus(String propertyCurrentStatus) {
        this.propertyCurrentStatus = propertyCurrentStatus;
    }

    public String getPropertyShowPhone() {
        return propertyShowPhone;
    }

    public String getPlanEnquiryCountBalance() {
        return planEnquiryCountBalance;
    }

    public void setPlanEnquiryCountBalance(String planEnquiryCountBalance) {
        this.planEnquiryCountBalance = planEnquiryCountBalance;
    }

    public void setPropertyShowPhone(String propertyShowPhone) {
        this.propertyShowPhone = propertyShowPhone;
    }

    public String getPropertySoldOutDate() {
        return propertySoldOutDate;
    }

    public void setPropertySoldOutDate(String propertySoldOutDate) {
        this.propertySoldOutDate = propertySoldOutDate;
    }

    public String getPropertyPlotArea() {
        return propertyPlotArea;
    }

    public void setPropertyPlotArea(String propertyPlotArea) {
        this.propertyPlotArea = propertyPlotArea;
    }

    public String getPropertyTypeIcon() {
        return propertyTypeIcon;
    }

    public void setPropertyTypeIcon(String propertyTypeIcon) {
        this.propertyTypeIcon = propertyTypeIcon;
    }

    public String getBluePrintimageURL() {
        return bluePrintimageURL;
    }

    public void setBluePrintimageURL(String bluePrintimageURL) {
        this.bluePrintimageURL = bluePrintimageURL;
    }

    public String getUserCompanyLogo() {
        return userCompanyLogo;
    }

    public void setUserCompanyLogo(String userCompanyLogo) {
        this.userCompanyLogo = userCompanyLogo;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getPropertyBathRoom() {
        return propertyBathRoom;
    }

    public void setPropertyBathRoom(String propertyBathRoom) {
        this.propertyBathRoom = propertyBathRoom;
    }

    public String getSimilerImageUrl() {
        return similerImageUrl;
    }

    public void setSimilerImageUrl(String similerImageUrl) {
        this.similerImageUrl = similerImageUrl;
    }

    public String getPropertySqrFeet() {
        return propertySqrFeet;
    }

    public void setPropertySqrFeet(String propertySqrFeet) {
        this.propertySqrFeet = propertySqrFeet;
    }

    public String getPropertyBuiltUpArea() {
        return propertyBuiltUpArea;
    }

    public void setPropertyBuiltUpArea(String propertyBuiltUpArea) {
        this.propertyBuiltUpArea = propertyBuiltUpArea;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getPricePerUnitValue() {
        return pricePerUnitValue;
    }

    public void setPricePerUnitValue(String pricePerUnitValue) {
        this.pricePerUnitValue = pricePerUnitValue;
    }

    public String getPropertyTypeKey() {
        return propertyTypeKey;
    }

    public void setPropertyTypeKey(String propertyTypeKey) {
        this.propertyTypeKey = propertyTypeKey;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<SavedVideoInfo> getPropertyVideos() {
        return propertyVideos;
    }

    public void setPropertyVideos(ArrayList<SavedVideoInfo> propertyVideos) {
        this.propertyVideos = propertyVideos;
    }

    public class PropertyImages {
        @SerializedName("images")
        private ArrayList<Images> images;
        @SerializedName("propCoverImage")
        private String propCoverImage;
        @SerializedName("imageUrl")
        private String imageUrl;

        public ArrayList<Images> getImages() {
            return images;
        }

        public void setImages(ArrayList<Images> images) {
            this.images = images;
        }

        public String getPropCoverImage() {
            return propCoverImage;
        }

        public void setPropCoverImage(String propCoverImage) {
            this.propCoverImage = propCoverImage;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public class Images {
        @SerializedName("propertyImageID")
        private String propertyImageID;
        @SerializedName("propertyImageName")
        private String propertyImageName;
        @SerializedName("propertyImageStatus")
        private String propertyImageStatus;

        public String getPropertyImageID() {
            return propertyImageID;
        }

        public void setPropertyImageID(String propertyImageID) {
            this.propertyImageID = propertyImageID;
        }

        public String getPropertyImageName() {
            return propertyImageName;
        }

        public void setPropertyImageName(String propertyImageName) {
            this.propertyImageName = propertyImageName;
        }

        public String getPropertyImageStatus() {
            return propertyImageStatus;
        }

        public void setPropertyImageStatus(String propertyImageStatus) {
            this.propertyImageStatus = propertyImageStatus;
        }
    }

    public String getAmenitiesImgPath() {
        return amenitiesImgPath;
    }

    public void setAmenitiesImgPath(String amenitiesImgPath) {
        this.amenitiesImgPath = amenitiesImgPath;
    }

    public String getPropertyLocality() {
        return propertyLocality;
    }

    public void setPropertyLocality(String propertyLocality) {
        this.propertyLocality = propertyLocality;
    }

    public String getPropertyLocationName() {
        return propertyLocationName;
    }

    public void setPropertyLocationName(String propertyLocationName) {
        this.propertyLocationName = propertyLocationName;
    }

    public String getProjectUserCompanyName() {
        return projectUserCompanyName;
    }

    public void setProjectUserCompanyName(String projectUserCompanyName) {
        this.projectUserCompanyName = projectUserCompanyName;
    }

    public String getPropertyBedRoom() {
        return propertyBedRoom;
    }

    public void setPropertyBedRoom(String propertyBedRoom) {
        this.propertyBedRoom = propertyBedRoom;
    }

    public String getPossessionDate() {
        return possessionDate;
    }

    public void setPossessionDate(String possessionDate) {
        this.possessionDate = possessionDate;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getPropertyLink() {
        return propertyLink;
    }

    public void setPropertyLink(String propertyLink) {
        this.propertyLink = propertyLink;
    }

    public String getPropertyStateName() {
        return propertyStateName;
    }

    public void setPropertyStateName(String propertyStateName) {
        this.propertyStateName = propertyStateName;
    }

    public ArrayList<BuyPropertiesModel> getSimilarProperties() {
        return similarProperties;
    }

    public void setSimilarProperties(ArrayList<BuyPropertiesModel> similarProperties) {
        this.similarProperties = similarProperties;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPossessionStatus() {
        return possessionStatus;
    }

    public void setPossessionStatus(String possessionStatus) {
        this.possessionStatus = possessionStatus;
    }

    public String getPropertyAddress1() {
        return propertyAddress1;
    }

    public void setPropertyAddress1(String propertyAddress1) {
        this.propertyAddress1 = propertyAddress1;
    }

    public String getPropertyAddress2() {
        return propertyAddress2;
    }

    public void setPropertyAddress2(String propertyAddress2) {
        this.propertyAddress2 = propertyAddress2;
    }

    public String getPropertyCityName() {
        return propertyCityName;
    }

    public void setPropertyCityName(String propertyCityName) {
        this.propertyCityName = propertyCityName;
    }

    public String getPropertyAddedDate() {
        return propertyAddedDate;
    }

    public void setPropertyAddedDate(String propertyAddedDate) {
        this.propertyAddedDate = propertyAddedDate;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public String getPropertyFeatured() {
        return propertyFeatured;
    }

    public void setPropertyFeatured(String propertyFeatured) {
        this.propertyFeatured = propertyFeatured;
    }

    public String getPropertySoldOutStatus() {
        return propertySoldOutStatus;
    }

    public void setPropertySoldOutStatus(String propertySoldOutStatus) {
        this.propertySoldOutStatus = propertySoldOutStatus;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getPricePerSqft() {
        return pricePerSqft;
    }

    public void setPricePerSqft(String pricePerSqft) {
        this.pricePerSqft = pricePerSqft;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getPropertyLat() {
        return propertyLat;
    }

    public void setPropertyLat(String propertyLat) {
        this.propertyLat = propertyLat;
    }

    public String getPropertyLong() {
        return propertyLong;
    }

    public void setPropertyLong(String propertyLong) {
        this.propertyLong = propertyLong;
    }

    public Integer getPropertyFavourite() {
        return propertyFavourite;
    }

    public void setPropertyFavourite(Integer propertyFavourite) {
        this.propertyFavourite = propertyFavourite;
    }

    public ArrayList<AmenitiesModel> getLstAmenities() {
        return lstAmenities;
    }

    public void setLstAmenities(ArrayList<AmenitiesModel> lstAmenities) {
        this.lstAmenities = lstAmenities;
    }

    public ArrayList<SpecificationModel> getPropertyAtrributes() {
        return propertyAtrributes;
    }

    public void setPropertyAtrributes(ArrayList<SpecificationModel> propertyAtrributes) {
        this.propertyAtrributes = propertyAtrributes;
    }

 /*   public ArrayList<MonthlyMortgage> getMonthlyMortgage() {
        return monthlyMortgage;
    }

    public void setMonthlyMortgage(ArrayList<MonthlyMortgage> monthlyMortgage) {
        this.monthlyMortgage = monthlyMortgage;
    }*/

    public PropertyImages getPropertyImages() {
        return propertyImages;
    }

    public void setPropertyImages(PropertyImages propertyImages) {
        this.propertyImages = propertyImages;
    }

    public String getAdditionalPhone_1() {
        return additionalPhone_1 != null ? additionalPhone_1 : "";
    }

    public void setAdditionalPhone_1(String additionalPhone_1) {
        this.additionalPhone_1 = additionalPhone_1;
    }

    public String getAdditionalPhone_2() {
        return additionalPhone_2 != null ? additionalPhone_2 : "";
    }

    public void setAdditionalPhone_2(String additionalPhone_2) {
        this.additionalPhone_2 = additionalPhone_2;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
