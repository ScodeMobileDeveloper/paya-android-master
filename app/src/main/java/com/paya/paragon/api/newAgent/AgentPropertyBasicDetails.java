package com.paya.paragon.api.newAgent;

import com.google.gson.annotations.SerializedName;

public class AgentPropertyBasicDetails {
    @SerializedName("id")
    private String id;
    @SerializedName("propertyName")
    private String propertyName;
    @SerializedName("propertyPrice")
    private String propertyPrice;
    @SerializedName("type")
    private String type;
    @SerializedName("CurrentStatus")
    private String CurrentStatus;
    @SerializedName("Purpose")
    private String Purpose;
    @SerializedName("PropertySoldStatus")
    private Object PropertySoldStatus;
    @SerializedName("IsNegotiable")
    private String IsNegotiable;
    @SerializedName("propertyAddress")
    private String propertyAddress;
    @SerializedName("propertyImageName")
    private String propertyImageName;
    @SerializedName("imageUrl")
    private String imageUrl;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrentStatus() {
        return CurrentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        CurrentStatus = currentStatus;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public Object getPropertySoldStatus() {
        return PropertySoldStatus;
    }

    public void setPropertySoldStatus(Object propertySoldStatus) {
        PropertySoldStatus = propertySoldStatus;
    }

    public String getIsNegotiable() {
        return IsNegotiable;
    }

    public void setIsNegotiable(String isNegotiable) {
        IsNegotiable = isNegotiable;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getPropertyImageName() {
        return propertyImageName;
    }

    public void setPropertyImageName(String propertyImageName) {
        this.propertyImageName = propertyImageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}
