package com.paya.paragon.api.index;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.budgetList.BudgetPriceData;

import java.util.ArrayList;

public class IndexDataBuyRent {
    @SerializedName("locations") @Expose private ArrayList<LocationInfo> locations;
    @SerializedName("propertyType") @Expose private ArrayList<PropertyTypeMain> propertyType;
    @SerializedName("buyMinPrice") @Expose private ArrayList<BudgetPriceData> buyMinPrice;
    @SerializedName("buyMaxPrice") @Expose private ArrayList<BudgetPriceData> buyMaxPrice;
    @SerializedName("rentMinPrice") @Expose private ArrayList<BudgetPriceData> rentMinPrice;
    @SerializedName("rentMaxPrice") @Expose private ArrayList<BudgetPriceData> rentMaxPrice;
    @SerializedName("imgPath") @Expose private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<BudgetPriceData> getBuyMinPrice() {
        return buyMinPrice;
    }

    public void setBuyMinPrice(ArrayList<BudgetPriceData> buyMinPrice) {
        this.buyMinPrice = buyMinPrice;
    }

    public ArrayList<BudgetPriceData> getBuyMaxPrice() {
        return buyMaxPrice;
    }

    public void setBuyMaxPrice(ArrayList<BudgetPriceData> buyMaxPrice) {
        this.buyMaxPrice = buyMaxPrice;
    }

    public ArrayList<BudgetPriceData> getRentMinPrice() {
        return rentMinPrice;
    }

    public void setRentMinPrice(ArrayList<BudgetPriceData> rentMinPrice) {
        this.rentMinPrice = rentMinPrice;
    }

    public ArrayList<BudgetPriceData> getRentMaxPrice() {
        return rentMaxPrice;
    }

    public void setRentMaxPrice(ArrayList<BudgetPriceData> rentMaxPrice) {
        this.rentMaxPrice = rentMaxPrice;
    }

    public ArrayList<LocationInfo> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<LocationInfo> locations) {
        this.locations = locations;
    }

    public ArrayList<PropertyTypeMain> getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(ArrayList<PropertyTypeMain> propertyType) {
        this.propertyType = propertyType;
    }



}
