package com.paya.paragon.api.openHouseSlots;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpenHouseSlotListResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("data") @Expose private OpenHouseSlotListData data;


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

    public OpenHouseSlotListData getData() {
        return data;
    }

    public void setData(OpenHouseSlotListData data) {
        this.data = data;
    }
}
