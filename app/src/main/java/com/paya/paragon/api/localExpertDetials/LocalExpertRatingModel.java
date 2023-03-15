package com.paya.paragon.api.localExpertDetials;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LocalExpertRatingModel {
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("userFirstName") @Expose private String userFirstName;
    @SerializedName("userLastName") @Expose private String userLastName;
    @SerializedName("userProfilePicture") @Expose private String userProfilePicture;
    @SerializedName("reviewID") @Expose private String reviewID;
    @SerializedName("reviewTitle") @Expose private String reviewTitle;
    @SerializedName("reviewText") @Expose private String reviewText;
    @SerializedName("reviewDate") @Expose private String userReviewedDate;
    @SerializedName("userRating") @Expose private String userRating;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getUserReviewedDate() {
        return userReviewedDate;
    }

    public void setUserReviewedDate(String userReviewedDate) {
        this.userReviewedDate = userReviewedDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }
}
