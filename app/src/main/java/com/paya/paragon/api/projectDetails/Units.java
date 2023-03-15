package com.paya.paragon.api.projectDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Units {
    @SerializedName("propertyID")
    @Expose
    private String propertyID;
    @SerializedName("propertyTypeID")
    @Expose
    private String propertyTypeID;
    @SerializedName("propertyName")
    @Expose
    private String propertyName;
    @SerializedName("propertyTypeName")
    @Expose
    private String propertyTypeName;

    @SerializedName("propertyTypeIcon")
    @Expose
    private String propertyTypeIcon;


    @SerializedName("propertyPrice")
    @Expose
    private String propertyPrice;
    @SerializedName("planPropertyDetails")
    @Expose
    private String planPropertyDetails;
    @SerializedName("propertySqrFeet")
    @Expose
    private String propertySqrFeet;
    @SerializedName("propertyPlotArea")
    @Expose
    private String propertyPlotArea; @SerializedName("propertyBedRoom")
    @Expose
    private String propertyBedRoom;
    @SerializedName("pricePerSqft")
    @Expose
    private String pricePerSqft;
    @SerializedName("floorPlans") @Expose private ArrayList<FloorPlans> floorPlans;
    @SerializedName("attributes") @Expose private ArrayList<AttributesModel> attributes;

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public String getPropertyTypeIcon() {
        return propertyTypeIcon;
    }

    public void setPropertyTypeIcon(String propertyTypeIcon) {
        this.propertyTypeIcon = propertyTypeIcon;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getPlanPropertyDetails() {
        return planPropertyDetails;
    }

    public void setPlanPropertyDetails(String planPropertyDetails) {
        this.planPropertyDetails = planPropertyDetails;
    }

    public String getPropertySqrFeet() {
        return propertySqrFeet;
    }

    public void setPropertySqrFeet(String propertySqrFeet) {
        this.propertySqrFeet = propertySqrFeet;
    }

    public String getPropertyPlotArea() {
        return propertyPlotArea;
    }

    public void setPropertyPlotArea(String propertyPlotArea) {
        this.propertyPlotArea = propertyPlotArea;
    }

    public String getPropertyBedRoom() {
        return propertyBedRoom;
    }

    public void setPropertyBedRoom(String propertyBedRoom) {
        this.propertyBedRoom = propertyBedRoom;
    }

    public String getPricePerSqft() {
        return pricePerSqft;
    }

    public void setPricePerSqft(String pricePerSqft) {
        this.pricePerSqft = pricePerSqft;
    }

    public ArrayList<FloorPlans> getFloorPlans() {
        return floorPlans;
    }

    public void setFloorPlans(ArrayList<FloorPlans> floorPlans) {
        this.floorPlans = floorPlans;
    }

    public ArrayList<AttributesModel> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<AttributesModel> attributes) {
        this.attributes = attributes;
    }
}
