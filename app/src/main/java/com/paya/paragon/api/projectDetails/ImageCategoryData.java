package com.paya.paragon.api.projectDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageCategoryData {
    @SerializedName("imageCatID") @Expose private String imageCatID;
    @SerializedName("imageCatName") @Expose private String imageCatName;
    @SerializedName("imgpath") @Expose private String imgpath;
    @SerializedName("images") @Expose private ArrayList<ProjectImageInfo> images;

    public String getImageCatID() {
        return imageCatID;
    }

    public void setImageCatID(String imageCatID) {
        this.imageCatID = imageCatID;
    }

    public String getImageCatName() {
        return imageCatName;
    }

    public void setImageCatName(String imageCatName) {
        this.imageCatName = imageCatName;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public ArrayList<ProjectImageInfo> getImages() {
        return images;
    }

    public void setImages(ArrayList<ProjectImageInfo> images) {
        this.images = images;
    }
}
