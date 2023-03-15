package com.paya.paragon.api.offerList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class OfferListData {
    @SerializedName("totalCount") @Expose private Integer totalCount;
    @SerializedName("enquiries") @Expose private ArrayList<OfferListItemData> enquiries;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<OfferListItemData> getEnquiries() {
        return enquiries;
    }

    public void setEnquiries(ArrayList<OfferListItemData> enquiries) {
        this.enquiries = enquiries;
    }
}
