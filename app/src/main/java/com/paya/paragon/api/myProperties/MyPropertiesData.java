package com.paya.paragon.api.myProperties;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MyPropertiesData {
    @SerializedName("totalCount") @Expose private Integer totalCount;
    @SerializedName("imgPath") @Expose private String imgPath;
    @SerializedName("proprtyList") @Expose private ArrayList<MyPropertiesItem> proprtyList;


    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public ArrayList<MyPropertiesItem> getProprtyList() {
        return proprtyList;
    }

    public void setProprtyList(ArrayList<MyPropertiesItem> proprtyList) {
        this.proprtyList = proprtyList;
    }

}
