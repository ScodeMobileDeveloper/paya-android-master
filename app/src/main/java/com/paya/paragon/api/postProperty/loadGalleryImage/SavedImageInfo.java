package com.paya.paragon.api.postProperty.loadGalleryImage;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class SavedImageInfo implements Serializable {
    @SerializedName("propertyImageID") @Expose private String propertyImageID;
    @SerializedName("propertyImageName") @Expose private String propertyImageName;
    @SerializedName("isCoverImage") @Expose private String isCoverImage;
    @SerializedName("propertyImageTitle") @Expose private String propertyImageTitle;
    @SerializedName("propertyImageAltTag") @Expose private String propertyImageAltTag;

    public String getPropertyImageID() {
        return propertyImageID;
    }

    public void setPropertyImageID(String propertyImageID) {
        this.propertyImageID = propertyImageID;
    }

    public String getPropertyImageName() {
        return propertyImageName;
    }

    public void setPropertyImageName(String propertyImageName) {
        this.propertyImageName = propertyImageName;
    }

    public String getIsCoverImage() {
        return isCoverImage;
    }

    public void setIsCoverImage(String isCoverImage) {
        this.isCoverImage = isCoverImage;
    }

    public String getPropertyImageTitle() {
        return propertyImageTitle;
    }

    public void setPropertyImageTitle(String propertyImageTitle) {
        this.propertyImageTitle = propertyImageTitle;
    }

    public String getPropertyImageAltTag() {
        return propertyImageAltTag;
    }

    public void setPropertyImageAltTag(String propertyImageAltTag) {
        this.propertyImageAltTag = propertyImageAltTag;
    }
}
