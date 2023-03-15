package com.paya.paragon.api.editPostProperty;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PostedPropertyGalleryInfo {
    @SerializedName("propertyImageID") @Expose private String propertyImageID;
    @SerializedName("propertyImageName") @Expose private String propertyImageName;
    @SerializedName("imageCatID") @Expose private String imageCatID;
    @SerializedName("isCoverImage") @Expose private String isCoverImage;

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

    public String getImageCatID() {
        return imageCatID;
    }

    public void setImageCatID(String imageCatID) {
        this.imageCatID = imageCatID;
    }

    public String getIsCoverImage() {
        return isCoverImage;
    }

    public void setIsCoverImage(String isCoverImage) {
        this.isCoverImage = isCoverImage;
    }
}
