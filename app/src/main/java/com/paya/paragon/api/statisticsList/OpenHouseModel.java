package com.paya.paragon.api.statisticsList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OpenHouseModel {
    @SerializedName("slotID") @Expose private String slotID;
    @SerializedName("propertyID") @Expose private String propertyID;
    @SerializedName("slotStart") @Expose private String slotStart;
    @SerializedName("slotEnd") @Expose private String slotEnd;

    public String getSlotID() {
        return slotID;
    }

    public void setSlotID(String slotID) {
        this.slotID = slotID;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getSlotStart() {
        return slotStart;
    }

    public void setSlotStart(String slotStart) {
        this.slotStart = slotStart;
    }

    public String getSlotEnd() {
        return slotEnd;
    }

    public void setSlotEnd(String slotEnd) {
        this.slotEnd = slotEnd;
    }
}
