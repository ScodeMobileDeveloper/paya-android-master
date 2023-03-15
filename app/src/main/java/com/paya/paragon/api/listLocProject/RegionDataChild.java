package com.paya.paragon.api.listLocProject;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegionDataChild {
    @SerializedName("id") @Expose private String id;
    @SerializedName("originalText") @Expose private String originalText;
    @SerializedName("longnitude") @Expose private String longnitude;
    @SerializedName("latitude") @Expose private String latitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getLongnitude() {
        return longnitude;
    }

    public void setLongnitude(String longnitude) {
        this.longnitude = longnitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "RegionDataChild{" +
                "id='" + id + '\'' +
                ", originalText='" + originalText + '\'' +
                ", longnitude='" + longnitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }

    public RegionDataChild(){

    }
    public RegionDataChild(String id, String originalText) {
        this.id = id;
        this.originalText = originalText;
    }

    public RegionDataChild(String id, String originalText, String longnitude, String latitude) {
        this.id = id;
        this.originalText = originalText;
        this.longnitude = longnitude;
        this.latitude = latitude;
    }
}
