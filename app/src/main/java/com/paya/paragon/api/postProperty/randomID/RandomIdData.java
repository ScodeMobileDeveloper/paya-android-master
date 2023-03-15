package com.paya.paragon.api.postProperty.randomID;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class RandomIdData {
    @SerializedName("randomID") @Expose private Integer randomID;
    @SerializedName("userOwnerID") @Expose private String userOwnerID;
    @SerializedName("propCount") @Expose private String propCount;
    @SerializedName("featCount") @Expose private String featCount;
    @SerializedName("userPlanList") @Expose private ArrayList<UserPlanInfo> userPlanList;

    public Integer getRandomID() {
        return randomID;
    }

    public void setRandomID(Integer randomID) {
        this.randomID = randomID;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(String userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    public String getPropCount() {
        return propCount;
    }

    public void setPropCount(String propCount) {
        this.propCount = propCount;
    }

    public String getFeatCount() {
        return featCount;
    }

    public void setFeatCount(String featCount) {
        this.featCount = featCount;
    }

    public ArrayList<UserPlanInfo> getUserPlanList() {
        return userPlanList;
    }

    public void setUserPlanList(ArrayList<UserPlanInfo> userPlanList) {
        this.userPlanList = userPlanList;
    }
}
