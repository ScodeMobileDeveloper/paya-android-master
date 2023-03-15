package com.paya.paragon.model;

import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class RequirementModel {
    private String requirementID;
    private String requirementAction;
    private String purpose;
    private String propertyTypeParentName;
    private String propertyTypeParentID;
    private ArrayList<String> propertyTypeNames;
    private ArrayList<String> propertyTypeIds;
    private ArrayList<String> propertyTypeIdsEdit;
    private String minPrice;
    private String minPriceValue;
    private String maxPrice;
    private String maxPriceValue;
    private String locality;
    private String reqBedroom;
    private String locationList;
    private String city;
    private String state;
    private String country;
    private String localitySearchText;
    private Double latitude;
    private Double longitude;
    private ArrayList<Integer> featuresSizeList;
    private ArrayList<AllAttributesData> attributeList;
    private String selectedAttributes;
    private String name;
    private String email;
    private String phone;
    private String countryCode;
    private String mortgage;

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }

    public String getReqBedroom() {
        return reqBedroom;
    }

    public void setReqBedroom(String reqBedroom) {
        this.reqBedroom = reqBedroom;
    }

    public String getLocationList() {
        return locationList;
    }

    public void setLocationList(String locationList) {
        this.locationList = locationList;
    }

    public String getRequirementID() {
        return requirementID;
    }

    public void setRequirementID(String requirementID) {
        this.requirementID = requirementID;
    }

    public String getRequirementAction() {
        return requirementAction;
    }

    public void setRequirementAction(String requirementAction) {
        this.requirementAction = requirementAction;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPropertyTypeParentName() {
        return propertyTypeParentName;
    }

    public void setPropertyTypeParentName(String propertyTypeParentName) {
        this.propertyTypeParentName = propertyTypeParentName;
    }

    public String getPropertyTypeParentID() {
        return propertyTypeParentID;
    }

    public void setPropertyTypeParentID(String propertyTypeParentID) {
        this.propertyTypeParentID = propertyTypeParentID;
    }

    public ArrayList<String> getPropertyTypeNames() {
        return propertyTypeNames;
    }

    public void setPropertyTypeNames(ArrayList<String> propertyTypeNames) {
        this.propertyTypeNames = propertyTypeNames;
    }

    public ArrayList<String> getPropertyTypeIds() {
        return propertyTypeIds;
    }

    public void setPropertyTypeIds(ArrayList<String> propertyTypeIds) {
        this.propertyTypeIds = propertyTypeIds;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMinPriceValue() {
        return minPriceValue;
    }

    public void setMinPriceValue(String minPriceValue) {
        this.minPriceValue = minPriceValue;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMaxPriceValue() {
        return maxPriceValue;
    }

    public void setMaxPriceValue(String maxPriceValue) {
        this.maxPriceValue = maxPriceValue;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocalitySearchText() {
        return localitySearchText;
    }

    public void setLocalitySearchText(String localitySearchText) {
        this.localitySearchText = localitySearchText;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Integer> getFeaturesSizeList() {
        return featuresSizeList;
    }

    public void setFeaturesSizeList(ArrayList<Integer> featuresSizeList) {
        this.featuresSizeList = featuresSizeList;
    }

    public ArrayList<AllAttributesData> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(ArrayList<AllAttributesData> attributeList) {
        this.attributeList = attributeList;
    }

    public String getSelectedAttributes() {
        return selectedAttributes;
    }

    public void setSelectedAttributes(String selectedAttributes) {
        this.selectedAttributes = selectedAttributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public ArrayList<String> getPropertyTypeIdsEdit() {
        return propertyTypeIdsEdit;
    }

    public void setPropertyTypeIdsEdit(ArrayList<String> propertyTypeIdsEdit) {
        this.propertyTypeIdsEdit = propertyTypeIdsEdit;
    }
}
