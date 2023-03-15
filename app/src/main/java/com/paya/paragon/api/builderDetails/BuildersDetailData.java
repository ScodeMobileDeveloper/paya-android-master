package com.paya.paragon.api.builderDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuildersDetailData {
    @SerializedName("builderDetail")  @Expose private BuilderDetail builderDetail;
    @SerializedName("imageURL")  @Expose private String imageURL;



    public BuilderDetail getBuilderDetail() {
        return builderDetail;
    }

    public void setBuilderDetail(BuilderDetail builderDetail) {
        this.builderDetail = builderDetail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
