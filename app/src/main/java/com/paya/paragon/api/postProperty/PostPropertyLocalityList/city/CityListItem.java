package com.paya.paragon.api.postProperty.PostPropertyLocalityList.city;



import com.google.gson.annotations.SerializedName;


public class CityListItem {

    @SerializedName("cityName")
    private String cityName;

    @SerializedName("cityLat")
    private String cityLat;

    @SerializedName("cityLng")
    private String cityLng;

    @SerializedName("cityID")
    private String cityID;

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

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }
}