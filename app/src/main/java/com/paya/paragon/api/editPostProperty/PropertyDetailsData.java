package com.paya.paragon.api.editPostProperty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.api.propertyDetails.SuggestedPropertyDetails;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PropertyDetailsData {
    @SerializedName("userName")private String userName;
    @SerializedName("languageID")private String languageID;
    @SerializedName("userFirstName")private String userFirstName;
    @SerializedName("userLastName")private String userLastName;
    @SerializedName("userPhone")private String userPhone;
    @SerializedName("userAddress1")private String userAddress1;
    @SerializedName("userAddress2")private String userAddress2;
    @SerializedName("userZip")private String userZip;
    @SerializedName("cityID")private String cityID;
    @SerializedName("countryID")private String countryID;
    @SerializedName("stateID")private String stateID;
    @SerializedName("userUrlKey")private String userUrlKey;
    @SerializedName("countryCode")private String countryCode;
    @SerializedName("cityLocID")private String cityLocID;
    @SerializedName("propertyID")private String propertyID;
    @SerializedName("propertyKey")private String propertyKey;
    @SerializedName("propertyTypeID")private String propertyTypeID;
    @SerializedName("propertyFeatured")private String propertyFeatured;
    @SerializedName("propertyPurpose")private String propertyPurpose;
    @SerializedName("propertyLatitude")private String propertyLatitude;
    @SerializedName("propertyLongitude")private String propertyLongitude;
    @SerializedName("propertyZipCode")private String propertyZipCode;
    @SerializedName("propertyStatus")private String propertyStatus;
    @SerializedName("type")private String type;
    @SerializedName("isNegotiable")private String isNegotiable;
    @SerializedName("priceOnReq")private String priceOnReq;
    @SerializedName("propertyCurrentStatus")private String propertyCurrentStatus;
    @SerializedName("propertyDetailsID")private String propertyDetailsID;
    @SerializedName("propertyName")private String propertyName;
    @SerializedName("propertyAddress1")private String propertyAddress1;
    @SerializedName("propertyAddress2")private String propertyAddress2;
    @SerializedName("propertyDescription")private String propertyDescription;
    @SerializedName("propertyLocality")private String propertyLocality;
    @SerializedName("countryName")private String countryName;
    @SerializedName("stateName")private String stateName;
    @SerializedName("cityName")private String cityName;
    @SerializedName("cityLocName")private String cityLocName;
    @SerializedName("propertyPrice")private String propertyPrice;
    @SerializedName("attributes")private ArrayList<AllAttributesData> attributes;
    @SerializedName("amenities")private ArrayList<AmenitiesModel> amenities;
    @SerializedName("selectedAmenities")private String selectedAmenities;
    @SerializedName("propertyMortgage")private String propertyMortgage;
    @SerializedName("propertyContactAgent")private String propertyContactAgent;
    @SerializedName("morgBankID")private String morgBankID;
    @SerializedName("propertyAltPhone")private String propertyAltPhone;
    @SerializedName("bluePrintimage")private String bluePrintimage;
    @SerializedName("propertyAltEmail")private String propertyAltEmail;
    @SerializedName("subCommunityID")private String subCommunityID;
    @SerializedName("communityID")private String parentCommunity;
    @SerializedName("subCommunityName")private String subCommunityName;
    @SerializedName("bankName")private String bankName;
    @SerializedName("propertyCheques")private String propertyCheques;
    @SerializedName("rentTerm")private String rentTerm;
    @SerializedName("threeSixtyView")private String threeSixtyView;
    @SerializedName("propertyShowPhone")private String propertyShowPhone;
    @SerializedName("subUserID")private String subAgentId;
    @SerializedName("additionalPhone1")private String additionalPhone1;
    @SerializedName("additionalPhone2")private String additionalPhone2;
    @SerializedName("phoneoneh")private String phoneNoOne;
    @SerializedName("phonetwoh")private String phoneNoTwo;
    @SerializedName("propertyPricePerM2")private String propertyPricePerM2;
    @SerializedName("total_price")private String total_price;
    @SerializedName("countryCodeone")private String countryCodeone;
    @SerializedName("countryCodeTwo")private String countryCodeTwo;


    @SerializedName("propertyPrices")
    @Expose
    private PropertyPrices propertyPrices;

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

    public String getPhoneNoOne() {
        return phoneNoOne;
    }

    public void setPhoneNoOne(String phoneNoOne) {
        this.phoneNoOne = phoneNoOne;
    }

    public String getPhoneNoTwo() {
        return phoneNoTwo;
    }

    public void setPhoneNoTwo(String phoneNoTwo) {
        this.phoneNoTwo = phoneNoTwo;
    }

    public String getPropertyShowPhone() {
        return propertyShowPhone;
    }

    public void setPropertyShowPhone(String propertyShowPhone) {
        this.propertyShowPhone = propertyShowPhone;
    }

    public String getBluePrintimage() {
        return bluePrintimage;
    }

    public void setBluePrintimage(String bluePrintimage) {
        this.bluePrintimage = bluePrintimage;
    }

    public String getThreeSixtyView() {
        return threeSixtyView;
    }

    public void setThreeSixtyView(String threeSixtyView) {
        this.threeSixtyView = threeSixtyView;
    }

    public String getPropertyCheques() {
        return propertyCheques;
    }

    public void setPropertyCheques(String propertyCheques) {
        this.propertyCheques = propertyCheques;
    }

    public String getRentTerm() {
        return rentTerm;
    }

    public void setRentTerm(String rentTerm) {
        this.rentTerm = rentTerm;
    }

    public String getSubCommunityName() {
        return subCommunityName;
    }

    public void setSubCommunityName(String subCommunityName) {
        this.subCommunityName = subCommunityName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getParentCommunity() {
        return parentCommunity;
    }

    public void setParentCommunity(String parentCommunity) {
        this.parentCommunity = parentCommunity;
    }

    public String getPropertyContactAgent() {
        return propertyContactAgent;
    }

    public void setPropertyContactAgent(String propertyContactAgent) {
        this.propertyContactAgent = propertyContactAgent;
    }

    public String getPropertyMortgage() {
        return propertyMortgage;
    }

    public void setPropertyMortgage(String propertyMortgage) {
        this.propertyMortgage = propertyMortgage;
    }

    public String getMorgBankID() {
        return morgBankID;
    }

    public void setMorgBankID(String morgBankID) {
        this.morgBankID = morgBankID;
    }

    public String getPropertyAltPhone() {
        return propertyAltPhone;
    }

    public void setPropertyAltPhone(String propertyAltPhone) {
        this.propertyAltPhone = propertyAltPhone;
    }

    public String getPropertyAltEmail() {
        return propertyAltEmail;
    }

    public void setPropertyAltEmail(String propertyAltEmail) {
        this.propertyAltEmail = propertyAltEmail;
    }

    public String getSubCommunityID() {
        return subCommunityID;
    }

    public void setSubCommunityID(String subCommunityID) {
        this.subCommunityID = subCommunityID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress1() {
        return userAddress1;
    }

    public void setUserAddress1(String userAddress1) {
        this.userAddress1 = userAddress1;
    }

    public String getUserAddress2() {
        return userAddress2;
    }

    public void setUserAddress2(String userAddress2) {
        this.userAddress2 = userAddress2;
    }

    public String getUserZip() {
        return userZip;
    }

    public void setUserZip(String userZip) {
        this.userZip = userZip;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getUserUrlKey() {
        return userUrlKey;
    }

    public void setUserUrlKey(String userUrlKey) {
        this.userUrlKey = userUrlKey;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityLocID() {
        return cityLocID;
    }

    public void setCityLocID(String cityLocID) {
        this.cityLocID = cityLocID;
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

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public String getPropertyFeatured() {
        return propertyFeatured;
    }

    public void setPropertyFeatured(String propertyFeatured) {
        this.propertyFeatured = propertyFeatured;
    }

    public String getPropertyPurpose() {
        return propertyPurpose;
    }

    public void setPropertyPurpose(String propertyPurpose) {
        this.propertyPurpose = propertyPurpose;
    }

    public String getPropertyLatitude() {
        return propertyLatitude;
    }

    public void setPropertyLatitude(String propertyLatitude) {
        this.propertyLatitude = propertyLatitude;
    }

    public String getPropertyLongitude() {
        return propertyLongitude;
    }

    public void setPropertyLongitude(String propertyLongitude) {
        this.propertyLongitude = propertyLongitude;
    }

    public String getPropertyZipCode() {
        return propertyZipCode;
    }

    public void setPropertyZipCode(String propertyZipCode) {
        this.propertyZipCode = propertyZipCode;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsNegotiable() {
        return isNegotiable;
    }

    public void setIsNegotiable(String isNegotiable) {
        this.isNegotiable = isNegotiable;
    }

    public String getPriceOnReq() {
        return priceOnReq;
    }

    public void setPriceOnReq(String priceOnReq) {
        this.priceOnReq = priceOnReq;
    }

    public String getPropertyCurrentStatus() {
        return propertyCurrentStatus;
    }

    public void setPropertyCurrentStatus(String propertyCurrentStatus) {
        this.propertyCurrentStatus = propertyCurrentStatus;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public String getPropertyLocality() {
        return propertyLocality;
    }

    public void setPropertyLocality(String propertyLocality) {
        this.propertyLocality = propertyLocality;
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

    public String getCityLocName() {
        return cityLocName;
    }

    public void setCityLocName(String cityLocName) {
        this.cityLocName = cityLocName;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public ArrayList<AllAttributesData> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<AllAttributesData> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<AmenitiesModel> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<AmenitiesModel> amenities) {
        this.amenities = amenities;
    }

    public String getSelectedAmenities() {
        return selectedAmenities;
    }

    public void setSelectedAmenities(String selectedAmenities) {
        this.selectedAmenities = selectedAmenities;
    }

    public String getPropertyDetailsID() {
        return propertyDetailsID;
    }

    public void setPropertyDetailsID(String propertyDetailsID) {
        this.propertyDetailsID = propertyDetailsID;
    }

    public String getSubAgentId() {
        return subAgentId;
    }

    public void setSubAgentId(String subAgentId) {
        this.subAgentId = subAgentId;
    }

    public String getAdditionalPhone1() {
        return additionalPhone1;
    }

    public void setAdditionalPhone1(String additionalPhone1) {
        this.additionalPhone1 = additionalPhone1;
    }

    public String getAdditionalPhone2() {
        return additionalPhone2;
    }

    public void setAdditionalPhone2(String additionalPhone2) {
        this.additionalPhone2 = additionalPhone2;
    }

    public String getPropertyPricePerM2() {
        return propertyPricePerM2;
    }

    public void setPropertyPricePerM2(String propertyPricePerM2) {
        this.propertyPricePerM2 = propertyPricePerM2;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public PropertyPrices getPropertyPrices() {
        return propertyPrices;
    }

    public void setPropertyPrices(PropertyPrices propertyPrices) {
        this.propertyPrices = propertyPrices;
    }

    public class PropertyPrices {

        @SerializedName("1")
        @Expose
        private String _1;
        @SerializedName("5")
        @Expose
        private String _5;

        public String get1() {
            return _1;
        }

        public void set1(String _1) {
            this._1 = _1;
        }

        public String get5() {
            return _5;
        }

        public void set5(String _5) {
            this._5 = _5;
        }

    }
}
