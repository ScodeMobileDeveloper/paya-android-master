package com.paya.paragon.api.saveSearch;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveSearchResponse {
   @SerializedName("response") @Expose private String Response;
   @SerializedName("message") @Expose private String message;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
