package com.paya.paragon.api.shoertlistedProperties;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ShortlistedPropertyListData {
    @SerializedName("totalCount") @Expose private String totalCount;
    @SerializedName("imgPath") @Expose private String imgPath;
    @SerializedName("propertylist")  @Expose private ArrayList<ShortlistedPropertyModel> propertylist;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public ArrayList<ShortlistedPropertyModel> getPropertyList() {
        return propertylist;
    }

    public void setPropertyList(ArrayList<ShortlistedPropertyModel> propertyLists) {
        this.propertylist = propertyLists;
    }
}
