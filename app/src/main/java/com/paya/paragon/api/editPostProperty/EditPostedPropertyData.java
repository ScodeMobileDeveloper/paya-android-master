package com.paya.paragon.api.editPostProperty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.postProperty.loadVideo.SavedVideoInfo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class EditPostedPropertyData {
    @SerializedName("propertyDet") @Expose private PropertyDetailsData propertyDetails;
    @SerializedName("gallery") @Expose private List<PostedPropertyGalleryInfo> gallery = null;
    @SerializedName("videos") @Expose private ArrayList<SavedVideoInfo> videos = null;
    @SerializedName("imagePath") @Expose private String imagePath;
    @SerializedName("bluePrintimagePath") @Expose private String bluePrintimagePath;

    public String getBluePrintimagePath() {
        return bluePrintimagePath;
    }

    public void setBluePrintimagePath(String bluePrintimagePath) {
        this.bluePrintimagePath = bluePrintimagePath;
    }

    public PropertyDetailsData getPropertyDetails() {
        return propertyDetails;
    }

    public void setPropertyDetails(PropertyDetailsData propertyDet) {
        this.propertyDetails = propertyDet;
    }

    public List<PostedPropertyGalleryInfo> getGallery() {
        return gallery;
    }

    public void setGallery(List<PostedPropertyGalleryInfo> gallery) {
        this.gallery = gallery;
    }

    public ArrayList<SavedVideoInfo> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<SavedVideoInfo> videos) {
        this.videos = videos;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
