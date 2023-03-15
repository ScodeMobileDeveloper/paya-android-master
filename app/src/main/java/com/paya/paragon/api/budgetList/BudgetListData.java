package com.paya.paragon.api.budgetList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class BudgetListData {
    @SerializedName("resReqMinPriceOptionsBuy") @Expose private ArrayList<BudgetPriceData> resReqMinPriceOptionsBuy;
    @SerializedName("resReqMaxPriceOptionsBuy") @Expose private ArrayList<BudgetPriceData> resReqMaxPriceOptionsBuy;
    @SerializedName("resReqMinPriceOptionsRent") @Expose private ArrayList<BudgetPriceData> resReqMinPriceOptionsRent;
    @SerializedName("resReqMaxPriceOptionsRent") @Expose private ArrayList<BudgetPriceData> resReqMaxPriceOptionsRent;

    public ArrayList<BudgetPriceData> getResReqMinPriceOptionsBuy() {
        return resReqMinPriceOptionsBuy;
    }

    public void setResReqMinPriceOptionsBuy(ArrayList<BudgetPriceData> resReqMinPriceOptionsBuy) {
        this.resReqMinPriceOptionsBuy = resReqMinPriceOptionsBuy;
    }

    public ArrayList<BudgetPriceData> getResReqMaxPriceOptionsBuy() {
        return resReqMaxPriceOptionsBuy;
    }

    public void setResReqMaxPriceOptionsBuy(ArrayList<BudgetPriceData> resReqMaxPriceOptionsBuy) {
        this.resReqMaxPriceOptionsBuy = resReqMaxPriceOptionsBuy;
    }

    public ArrayList<BudgetPriceData> getResReqMinPriceOptionsRent() {
        return resReqMinPriceOptionsRent;
    }

    public void setResReqMinPriceOptionsRent(ArrayList<BudgetPriceData> resReqMinPriceOptionsRent) {
        this.resReqMinPriceOptionsRent = resReqMinPriceOptionsRent;
    }

    public ArrayList<BudgetPriceData> getResReqMaxPriceOptionsRent() {
        return resReqMaxPriceOptionsRent;
    }

    public void setResReqMaxPriceOptionsRent(ArrayList<BudgetPriceData> resReqMaxPriceOptionsRent) {
        this.resReqMaxPriceOptionsRent = resReqMaxPriceOptionsRent;
    }
}
