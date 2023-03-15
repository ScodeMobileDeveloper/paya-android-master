package com.paya.paragon.api.myPropertyEnquiry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyPropertyEnquiryData {
    @SerializedName("totalcount") @Expose private Integer totalCount;
    @SerializedName("enquiries") @Expose private ArrayList<PropertyEnquiryInfo> enquiryList;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<PropertyEnquiryInfo> getEnquiryList() {
        return enquiryList;
    }

    public void setEnquiryList(ArrayList<PropertyEnquiryInfo> enquiryList) {
        this.enquiryList = enquiryList;
    }
}
