package com.paya.paragon.api.postProperty.loadGalleryImageEdit;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.editPostProperty.PostedPropertyGalleryInfo;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class LoadSavedImageEditResponse {
    @SerializedName("response") @Expose private String response;
    @SerializedName("code") @Expose private Integer code;
    @SerializedName("message") @Expose private String message;
    @SerializedName("imgUrl") @Expose private String imgUrl;
    @SerializedName("data") @Expose private ArrayList<PostedPropertyGalleryInfo> data;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ArrayList<PostedPropertyGalleryInfo> getData() {
        return data;
    }

    public void setData(ArrayList<PostedPropertyGalleryInfo> data) {
        this.data = data;
    }
}
