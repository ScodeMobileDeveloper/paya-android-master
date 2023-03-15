package com.paya.paragon.api.editPostProperty;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PostedPropertyVideoInfo {
    @SerializedName("propertyVideoID") @Expose private String propertyVideoID;
    @SerializedName("propertyVideoType") @Expose private String propertyVideoType;
    @SerializedName("propertyVideo") @Expose private String propertyVideo;
    @SerializedName("propertyVideoYoutubeID") @Expose private String propertyVideoYoutubeID;
    @SerializedName("propertyVideoTitle") @Expose private String propertyVideoTitle;
    @SerializedName("propertyVideoDescription") @Expose private String propertyVideoDescription;

    public String getPropertyVideoID() {
        return propertyVideoID;
    }

    public void setPropertyVideoID(String propertyVideoID) {
        this.propertyVideoID = propertyVideoID;
    }

    public String getPropertyVideoType() {
        return propertyVideoType;
    }

    public void setPropertyVideoType(String propertyVideoType) {
        this.propertyVideoType = propertyVideoType;
    }

    public String getPropertyVideo() {
        return propertyVideo;
    }

    public void setPropertyVideo(String propertyVideo) {
        this.propertyVideo = propertyVideo;
    }

    public String getPropertyVideoYoutubeID() {
        return propertyVideoYoutubeID;
    }

    public void setPropertyVideoYoutubeID(String propertyVideoYoutubeID) {
        this.propertyVideoYoutubeID = propertyVideoYoutubeID;
    }

    public String getPropertyVideoTitle() {
        return propertyVideoTitle;
    }

    public void setPropertyVideoTitle(String propertyVideoTitle) {
        this.propertyVideoTitle = propertyVideoTitle;
    }

    public String getPropertyVideoDescription() {
        return propertyVideoDescription;
    }

    public void setPropertyVideoDescription(String propertyVideoDescription) {
        this.propertyVideoDescription = propertyVideoDescription;
    }
}
