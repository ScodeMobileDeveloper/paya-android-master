package com.paya.paragon.api.listSettings;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ListSettingsData {
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("userNewsletterSubscribeStatus") @Expose private String userNewsletterSubscribeStatus;
    @SerializedName("userPropertyAlerts") @Expose private String userPropertyAlerts;
    @SerializedName("userPropertyAlertType") @Expose private String userPropertyAlertType;
    @SerializedName("userSmsAlerts") @Expose private String userSmsAlerts;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserNewsletterSubscribeStatus() {
        return userNewsletterSubscribeStatus;
    }

    public void setUserNewsletterSubscribeStatus(String userNewsletterSubscribeStatus) {
        this.userNewsletterSubscribeStatus = userNewsletterSubscribeStatus;
    }

    public String getUserPropertyAlerts() {
        return userPropertyAlerts;
    }

    public void setUserPropertyAlerts(String userPropertyAlerts) {
        this.userPropertyAlerts = userPropertyAlerts;
    }

    public String getUserPropertyAlertType() {
        return userPropertyAlertType;
    }

    public void setUserPropertyAlertType(String userPropertyAlertType) {
        this.userPropertyAlertType = userPropertyAlertType;
    }

    public String getUserSmsAlerts() {
        return userSmsAlerts;
    }

    public void setUserSmsAlerts(String userSmsAlerts) {
        this.userSmsAlerts = userSmsAlerts;
    }
}
