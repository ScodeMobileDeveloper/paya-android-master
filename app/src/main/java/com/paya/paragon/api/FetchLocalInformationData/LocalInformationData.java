package com.paya.paragon.api.FetchLocalInformationData;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalInformationData {
    @SerializedName("infoName") @Expose private String infoName;
    @SerializedName("infoLat") @Expose private String infoLat;
    @SerializedName("infoLng") @Expose private String infoLng;
    @SerializedName("infoAddress") @Expose private String infoAddress;

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoLat() {
        return infoLat;
    }

    public void setInfoLat(String infoLat) {
        this.infoLat = infoLat;
    }

    public String getInfoLng() {
        return infoLng;
    }

    public void setInfoLng(String infoLng) {
        this.infoLng = infoLng;
    }

    public String getInfoAddress() {
        return infoAddress;
    }

    public void setInfoAddress(String infoAddress) {
        this.infoAddress = infoAddress;
    }
}
