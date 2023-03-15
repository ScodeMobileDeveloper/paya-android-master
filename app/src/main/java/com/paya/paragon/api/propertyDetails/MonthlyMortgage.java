package com.paya.paragon.api.propertyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonthlyMortgage {
    @SerializedName("bank") @Expose
    private String bank;
    @SerializedName("interest") @Expose private String interest;
    @SerializedName("bankDownpayment") @Expose private String bankDownpayment;
    @SerializedName("bankID") @Expose private String bankID;
    @SerializedName("bankLogo") @Expose private String bankLogo;
    @SerializedName("propertyPrice") @Expose private String propertyPrice;
    @SerializedName("year_term") @Expose private String year_term;

    @SerializedName("down_payment") @Expose private String down_payment;
    @SerializedName("financing_price") @Expose private String financing_price;
    @SerializedName("monthly_payment") @Expose private String monthly_payment;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getBankDownpayment() {
        return bankDownpayment;
    }

    public void setBankDownpayment(String bankDownpayment) {
        this.bankDownpayment = bankDownpayment;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getYear_term() {
        return year_term;
    }

    public void setYear_term(String year_term) {
        this.year_term = year_term;
    }

    public String getDown_payment() {
        return down_payment;
    }

    public void setDown_payment(String down_payment) {
        this.down_payment = down_payment;
    }

    public String getFinancing_price() {
        return financing_price;
    }

    public void setFinancing_price(String financing_price) {
        this.financing_price = financing_price;
    }

    public String getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(String monthly_payment) {
        this.monthly_payment = monthly_payment;
    }
}
