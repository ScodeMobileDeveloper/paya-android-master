package com.paya.paragon.api.postProperty.communityList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CommunityNameData {

    @SerializedName("communtiy") @Expose private String communtiy;

    public String getCommuntiy() {
        return communtiy;
    }

    public void setCommuntiy(String communtiy) {
        this.communtiy = communtiy;
    }
}
