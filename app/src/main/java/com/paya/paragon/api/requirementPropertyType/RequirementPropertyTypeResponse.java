package com.paya.paragon.api.requirementPropertyType;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.budgetList.BudgetListData;
import com.paya.paragon.api.index.PropertyTypeMain;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class RequirementPropertyTypeResponse {
    @SerializedName("response") @Expose private String response;
    @SerializedName("code") @Expose private Integer code;
    @SerializedName("message") @Expose private String message;
    @SerializedName("data") @Expose private ArrayList<PropertyTypeMain> data;
    @SerializedName("priceList") @Expose private BudgetListData priceList;
    @SerializedName("imagePath") @Expose private String imagePath;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<PropertyTypeMain> getData() {
        return data;
    }

    public void setData(ArrayList<PropertyTypeMain> data) {
        this.data = data;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BudgetListData getPriceList() {
        return priceList;
    }

    public void setPriceList(BudgetListData priceList) {
        this.priceList = priceList;
    }
}
