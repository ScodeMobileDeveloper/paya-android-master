package com.paya.paragon.api.agentProperty;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentPropertyModel {
    @SerializedName("propertyID") @Expose private String propertyID;
    @SerializedName("type") @Expose private String type;
    @SerializedName("propertyKey") @Expose private String propertyKey;
    @SerializedName("propertyUnitPriceRange") @Expose private String propertyPrice;
    @SerializedName("propertyName") @Expose private String propertyName;
    @SerializedName("propertyBedRoom") @Expose private String propertyBedRoom;
    @SerializedName("pricePerSqft") @Expose private String propertyUnitPricePerSqft;
    @SerializedName("propertyCoverImage") @Expose private String propertyCoverImage;
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
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

    public String getPropertyBedRoom() {
        return propertyBedRoom;
    }

    public void setPropertyBedRoom(String propertyBedRoom) {
        this.propertyBedRoom = propertyBedRoom;
    }

    public String getPropertyUnitPricePerSqft() {
        return propertyUnitPricePerSqft;
    }

    public void setPropertyUnitPricePerSqft(String propertyUnitPricePerSqft) {
        this.propertyUnitPricePerSqft = propertyUnitPricePerSqft;
    }

    public String getPropertyCoverImage() {
        return propertyCoverImage;
    }

    public void setPropertyCoverImage(String propertyCoverImage) {
        this.propertyCoverImage = propertyCoverImage;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }
}
