package com.paya.paragon.api.myPlan;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MyPlanData {
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("planID") @Expose private String planID;
    @SerializedName("planTitle") @Expose private String planTitle;
    @SerializedName("planPrice") @Expose private String planPrice;
    @SerializedName("planPurchaseDate") @Expose private String planPurchaseDate;
    @SerializedName("planExpiryDate") @Expose private String planExpiryDate;
    @SerializedName("currencySymbolLeft") @Expose private String currencySymbolLeft;
    @SerializedName("currencySymbolRight") @Expose private String currencySymbolRight;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(String planPrice) {
        this.planPrice = planPrice;
    }

    public String getPlanPurchaseDate() {
        return planPurchaseDate;
    }

    public void setPlanPurchaseDate(String planPurchaseDate) {
        this.planPurchaseDate = planPurchaseDate;
    }

    public String getPlanExpiryDate() {
        return planExpiryDate;
    }

    public void setPlanExpiryDate(String planExpiryDate) {
        this.planExpiryDate = planExpiryDate;
    }

    public String getCurrencySymbolLeft() {
        return currencySymbolLeft;
    }

    public void setCurrencySymbolLeft(String currencySymbolLeft) {
        this.currencySymbolLeft = currencySymbolLeft;
    }

    public String getCurrencySymbolRight() {
        return currencySymbolRight;
    }

    public void setCurrencySymbolRight(String currencySymbolRight) {
        this.currencySymbolRight = currencySymbolRight;
    }
}
