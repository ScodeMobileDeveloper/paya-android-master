package com.paya.paragon.api.viewingRequests;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ViewingRequestData {
    @SerializedName("scheduleName") @Expose private String scheduleName;
    @SerializedName("scheduleMessage") @Expose private String scheduleMessage;
    @SerializedName("scheduleEmail") @Expose private String scheduleEmail;
    @SerializedName("propertyID") @Expose private String propertyID;
    @SerializedName("schedulePhone") @Expose private String schedulePhone;
    @SerializedName("propertyName") @Expose private String propertyName;
    @SerializedName("scheduleDate") @Expose private String scheduleDate;
    @SerializedName("slotStart") @Expose private String slotStart;
    @SerializedName("slotEnd") @Expose private String slotEnd;
    @SerializedName("slotID") @Expose private String slotID;

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getScheduleMessage() {
        return scheduleMessage;
    }

    public void setScheduleMessage(String scheduleMessage) {
        this.scheduleMessage = scheduleMessage;
    }

    public String getScheduleEmail() {
        return scheduleEmail;
    }

    public void setScheduleEmail(String scheduleEmail) {
        this.scheduleEmail = scheduleEmail;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getSchedulePhone() {
        return schedulePhone;
    }

    public void setSchedulePhone(String schedulePhone) {
        this.schedulePhone = schedulePhone;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
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

    public String getSlotID() {
        return slotID;
    }

    public void setSlotID(String slotID) {
        this.slotID = slotID;
    }
}
