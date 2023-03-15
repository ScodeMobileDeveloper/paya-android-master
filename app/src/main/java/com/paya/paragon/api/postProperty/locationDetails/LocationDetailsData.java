package com.paya.paragon.api.postProperty.locationDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LocationDetailsData {
    @SerializedName("city") @Expose private String city;
    @SerializedName("latitude") @Expose private String latitude;
    @SerializedName("longitude") @Expose private String longitude;
    @SerializedName("country") @Expose private String country;

    public LocationDetailsData(){
        this.city = "";
        this.latitude = "";
        this.longitude = "";
        this.country = "";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
