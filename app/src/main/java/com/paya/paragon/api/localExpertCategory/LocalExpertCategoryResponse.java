package com.paya.paragon.api.localExpertCategory;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocalExpertCategoryResponse {
    @SerializedName("response") @Expose
    private String Response;
    @SerializedName("code") @Expose
    private Integer Code;
    @SerializedName("message") @Expose
    private String Message;
    @SerializedName("IconPath") @Expose
    private String IconPath;
    @SerializedName("data") @Expose
    private ArrayList<LocalExpertCategoryData> data;

    public String getIconPath() {
        return IconPath;
    }

    public void setIconPath(String iconPath) {
        IconPath = iconPath;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public ArrayList<LocalExpertCategoryData> getData() {
        return data;
    }

    public void setData(ArrayList<LocalExpertCategoryData> data) {
        this.data = data;
    }
}
