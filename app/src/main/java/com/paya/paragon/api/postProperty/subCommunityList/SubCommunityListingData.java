package com.paya.paragon.api.postProperty.subCommunityList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SubCommunityListingData {
    @SerializedName("communityID") @Expose private String communityID;
    @SerializedName("communityName") @Expose private String communityName;
    @SerializedName("communityParent") @Expose private String communityParent;

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityParent() {
        return communityParent;
    }

    public void setCommunityParent(String communityParent) {
        this.communityParent = communityParent;
    }
}
