package com.paya.paragon.api.index;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LocationInfo {
    @SerializedName("cityID") @Expose private String cityID;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("cityLat") @Expose private String cityLat;
    @SerializedName("cityLng") @Expose private String cityLng;
    private boolean isLocationSelected;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityLat() {
        return cityLat;
    }

    public void setCityLat(String cityLat) {
        this.cityLat = cityLat;
    }

    public String getCityLng() {
        return cityLng;
    }

    public void setCityLng(String cityLng) {
        this.cityLng = cityLng;
    }

    public boolean isLocationSelected() {
        return isLocationSelected;
    }

    public void setLocationSelected(boolean locationSelected) {
        isLocationSelected = locationSelected;
    }
}
