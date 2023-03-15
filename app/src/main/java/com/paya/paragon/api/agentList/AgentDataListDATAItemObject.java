package com.paya.paragon.api.agentList;

import com.google.gson.annotations.SerializedName;

public class AgentDataListDATAItemObject {

    @SerializedName("logo")
    private String logo;
    @SerializedName("imageBaseUrl")
    private String imageBaseUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("about")
    private String about;
    @SerializedName("phone")
    private String phone;
    @SerializedName("userID")
    private String userID;
    @SerializedName("languageIDs")
    private String languageIDs;
    @SerializedName("countProperties")
    private int countProperties;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        this.imageBaseUrl = imageBaseUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLanguageIDs() {
        return languageIDs;
    }

    public void setLanguageIDs(String languageIDs) {
        this.languageIDs = languageIDs;
    }

    public int getCountProperties() {
        return countProperties;
    }

    public void setCountProperties(int countProperties) {
        this.countProperties = countProperties;
    }
}
