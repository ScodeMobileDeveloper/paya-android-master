package com.paya.paragon.api.enquiryList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class EnquiryListData {
    @SerializedName("totalCount") @Expose private Integer totalCount;
    @SerializedName("enquiries") @Expose private ArrayList<EnquiryListItemData> enquiries;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<EnquiryListItemData> getEnquiries() {
        return enquiries;
    }

    public void setEnquiries(ArrayList<EnquiryListItemData> enquiries) {
        this.enquiries = enquiries;
    }
}
