package com.paya.paragon.api.indexListing;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.model.ProjectGalleryModel;

import java.util.ArrayList;

public class IndexListingData {
    @SerializedName("ProjectGallery")  @Expose private ArrayList<ProjectGalleryModel> ProjectGallery;
    @SerializedName("galleryImagePath")  @Expose private String galleryImagePath;

    public String getGalleryImagePath() {
        return galleryImagePath;
    }

    public void setGalleryImagePath(String galleryImagePath) {
        this.galleryImagePath = galleryImagePath;
    }

    public ArrayList<ProjectGalleryModel> getProjectGallery() {
        return ProjectGallery;
    }

    public void setProjectGallery(ArrayList<ProjectGalleryModel> projectGallery) {
        ProjectGallery = projectGallery;
    }
}
