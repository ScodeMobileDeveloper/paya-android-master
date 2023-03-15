package com.paya.paragon.api.getProfileDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfileData {
    @SerializedName("imgPath") @Expose private String imgPath;
    @SerializedName("imgCompanyLogoPath") @Expose private String imgCompanyLogoPath;
    @SerializedName("profileDetails") @Expose private GetProfileDetailsData profileDetails;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public GetProfileDetailsData getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(GetProfileDetailsData profileDetails) {
        this.profileDetails = profileDetails;
    }

    public String getImgCompanyLogoPath() {
        return imgCompanyLogoPath;
    }

    public void setImgCompanyLogoPath(String imgCompanyLogoPath) {
        this.imgCompanyLogoPath = imgCompanyLogoPath;
    }
}
