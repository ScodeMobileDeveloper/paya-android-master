package com.paya.paragon.api.planPayment;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanPaymentResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private String code;
    @SerializedName("message") @Expose private String message;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
