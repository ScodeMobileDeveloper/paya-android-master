package com.paya.paragon.api.localExpertDetials;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LocalExpertDetailsData {
    @SerializedName("imageURL") @Expose private String imageURL;
    @SerializedName("profileUrl") @Expose private String profileUrl;
    @SerializedName("reviewUrl") @Expose private String reviewUrl;
    @SerializedName("seviceURL") @Expose private String seviceURL;
    @SerializedName("resVoucher") @Expose private String resVoucher;
    @SerializedName("voucherHighlights") @Expose private String voucherHighlights;
    @SerializedName("business") @Expose private LocalExpertInfo business;
    @SerializedName("rating") @Expose private ArrayList<LocalExpertRatingModel> rating;
    @SerializedName("services") @Expose private ArrayList<LocalExpertServiceModel> services;
    public String getVoucherHighlights() {
        return voucherHighlights;
    }



    public void setVoucherHighlights(String voucherHighlights) {
        this.voucherHighlights = voucherHighlights;
    }
    public String getSeviceURL() {
        return seviceURL;
    }

    public void setSeviceURL(String seviceURL) {
        this.seviceURL = seviceURL;
    }

    public ArrayList<LocalExpertServiceModel> getServices() {
        return services;
    }

    public void setServices(ArrayList<LocalExpertServiceModel> services) {
        this.services = services;
    }

    public String getResVoucher() {
        return resVoucher;
    }

    public void setResVoucher(String resVoucher) {
        this.resVoucher = resVoucher;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public LocalExpertInfo getBusiness() {
        return business;
    }

    public void setBusiness(LocalExpertInfo business) {
        this.business = business;
    }

    public ArrayList<LocalExpertRatingModel> getRating() {
        return rating;
    }

    public void setRating(ArrayList<LocalExpertRatingModel> rating) {
        this.rating = rating;
    }
}
