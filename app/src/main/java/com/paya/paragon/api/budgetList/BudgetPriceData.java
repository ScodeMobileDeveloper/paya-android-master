package com.paya.paragon.api.budgetList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BudgetPriceData {
    @SerializedName("price") @Expose private String price;
    @SerializedName("priceValue") @Expose private String priceValue;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }
}
