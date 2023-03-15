package com.paya.paragon.api.projectDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ProjectVideoInfo {
    @SerializedName("projectVideoID") @Expose private String projectVideoID;
    @SerializedName("projectVideoType") @Expose private String projectVideoType;
    @SerializedName("projectVideo") @Expose private String projectVideo;
    @SerializedName("projectVideoYoutubeID") @Expose private String projectVideoYoutubeID;
    @SerializedName("projectVideoTitle") @Expose private String projectVideoTitle;
    @SerializedName("projectVideoDesc") @Expose private String projectVideoDesc;

    public String getProjectVideoID() {
        return projectVideoID;
    }

    public void setProjectVideoID(String projectVideoID) {
        this.projectVideoID = projectVideoID;
    }

    public String getProjectVideoType() {
        return projectVideoType;
    }

    public void setProjectVideoType(String projectVideoType) {
        this.projectVideoType = projectVideoType;
    }

    public String getProjectVideo() {
        return projectVideo;
    }

    public void setProjectVideo(String projectVideo) {
        this.projectVideo = projectVideo;
    }

    public String getProjectVideoYoutubeID() {
        return projectVideoYoutubeID;
    }

    public void setProjectVideoYoutubeID(String projectVideoYoutubeID) {
        this.projectVideoYoutubeID = projectVideoYoutubeID;
    }

    public String getProjectVideoTitle() {
        return projectVideoTitle;
    }

    public void setProjectVideoTitle(String projectVideoTitle) {
        this.projectVideoTitle = projectVideoTitle;
    }

    public String getProjectVideoDesc() {
        return projectVideoDesc;
    }

    public void setProjectVideoDesc(String projectVideoDesc) {
        this.projectVideoDesc = projectVideoDesc;
    }
}
