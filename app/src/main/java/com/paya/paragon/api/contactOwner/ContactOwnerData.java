package com.paya.paragon.api.contactOwner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactOwnerData {
    @SerializedName("enquiryID") @Expose private String enquiryID;
    @SerializedName("OwnerName") @Expose private String OwnerName;
    @SerializedName("userPhone") @Expose private String userPhone;
    @SerializedName("Key") @Expose private String Key;

    public String getEnquiryID() {
        return enquiryID;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public String getUserPhone() {
        return userPhone;
    }



    public String getKey() {
        return Key;
    }


}
