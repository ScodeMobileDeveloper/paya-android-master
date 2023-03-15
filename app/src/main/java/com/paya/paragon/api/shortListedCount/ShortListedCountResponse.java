package com.paya.paragon.api.shortListedCount;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShortListedCountResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("data") @Expose private ShortListedCountData shortListedCountData;

    public ShortListedCountData getShortListedCountData() {
        return shortListedCountData;
    }

    public void setShortListedCountData(ShortListedCountData shortListedCountData) {
        this.shortListedCountData = shortListedCountData;
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

    public class ShortListedCountData {
        @SerializedName("totalFavorites") @Expose private String totalFavorites;
        @SerializedName("settingsPhoneNum") @Expose private String settingsPhoneNum;
        @SerializedName("settingsEmail") @Expose private String settingsEmail;

        public String getSettingsEmail() {
            return settingsEmail;
        }

        public void setSettingsEmail(String settingsEmail) {
            this.settingsEmail = settingsEmail;
        }

        public String getTotalFavorites() {
            return totalFavorites;
        }

        public void setTotalFavorites(String totalFavorites) {
            this.totalFavorites = totalFavorites;
        }

        public String getSettingsPhoneNum() {
            return settingsPhoneNum;
        }

        public void setSettingsPhoneNum(String settingsPhoneNum) {
            this.settingsPhoneNum = settingsPhoneNum;
        }
    }

}
