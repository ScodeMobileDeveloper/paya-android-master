package com.paya.paragon.api.localExpertDetials;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalExpertGalleryImgsModel {

    @SerializedName("galleryImageID")
    @Expose
    private String galleryImageID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("imageCatID")
    @Expose
    private String imageCatID;
    @SerializedName("galleryImageName")
    @Expose
    private String galleryImageName;
    @SerializedName("galleryImageStatus")
    @Expose
    private String galleryImageStatus;
    @SerializedName("galleryImageAddedDate")
    @Expose
    private String galleryImageAddedDate;

    public String getGalleryImageID() {
        return galleryImageID;
    }

    public void setGalleryImageID(String galleryImageID) {
        this.galleryImageID = galleryImageID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImageCatID() {
        return imageCatID;
    }

    public void setImageCatID(String imageCatID) {
        this.imageCatID = imageCatID;
    }

    public String getGalleryImageName() {
        return galleryImageName;
    }

    public void setGalleryImageName(String galleryImageName) {
        this.galleryImageName = galleryImageName;
    }

    public String getGalleryImageStatus() {
        return galleryImageStatus;
    }

    public void setGalleryImageStatus(String galleryImageStatus) {
        this.galleryImageStatus = galleryImageStatus;
    }

    public String getGalleryImageAddedDate() {
        return galleryImageAddedDate;
    }

    public void setGalleryImageAddedDate(String galleryImageAddedDate) {
        this.galleryImageAddedDate = galleryImageAddedDate;
    }

}
