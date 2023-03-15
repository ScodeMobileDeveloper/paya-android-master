package com.paya.paragon.api.statisticsList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StatisticsModel {
    @SerializedName("date") @Expose private String date;
    @SerializedName("listhits") @Expose private String listhits;
    @SerializedName("detailhits") @Expose private String detailhits;
    @SerializedName("phonehits") @Expose private String phonehits;
    @SerializedName("emailhits") @Expose private String emailhits;
    @SerializedName("likes") @Expose private String likes;
    @SerializedName("favorite") @Expose private String favorite;
    @SerializedName("propertyName") @Expose private String propertyName;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getListhits() {
        return listhits;
    }

    public void setListhits(String listhits) {
        this.listhits = listhits;
    }

    public String getDetailhits() {
        return detailhits;
    }

    public void setDetailhits(String detailhits) {
        this.detailhits = detailhits;
    }

    public String getPhonehits() {
        return phonehits;
    }

    public void setPhonehits(String phonehits) {
        this.phonehits = phonehits;
    }

    public String getEmailhits() {
        return emailhits;
    }

    public void setEmailhits(String emailhits) {
        this.emailhits = emailhits;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
