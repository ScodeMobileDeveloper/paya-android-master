package com.paya.paragon.api.myFlags;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MyFlagsData {
    @SerializedName("reportID") @Expose private String reportID;
    @SerializedName("reportType") @Expose private String reportType;
    @SerializedName("reportFlagID") @Expose private String reportFlagID;
    @SerializedName("reportComment") @Expose private String reportComment;
    @SerializedName("reportItemID") @Expose private String reportItemID;
    @SerializedName("reportUserID") @Expose private String reportUserID;
    @SerializedName("reportIP") @Expose private String reportIP;
    @SerializedName("reportStatus") @Expose private String reportStatus;
    @SerializedName("reportDate") @Expose private String reportDate;
    @SerializedName("reportReplyComment") @Expose private String reportReplyComment;
    @SerializedName("reportUserFullName") @Expose private String reportUserFullName;
    @SerializedName("reportUserEmailID") @Expose private String reportUserEmailID;
    @SerializedName("flagTitle") @Expose private String flagTitle;
    @SerializedName("username") @Expose private String username;
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("reportItemName") @Expose private String reportItemName;

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportFlagID() {
        return reportFlagID;
    }

    public void setReportFlagID(String reportFlagID) {
        this.reportFlagID = reportFlagID;
    }

    public String getReportComment() {
        return reportComment;
    }

    public void setReportComment(String reportComment) {
        this.reportComment = reportComment;
    }

    public String getReportItemID() {
        return reportItemID;
    }

    public void setReportItemID(String reportItemID) {
        this.reportItemID = reportItemID;
    }

    public String getReportUserID() {
        return reportUserID;
    }

    public void setReportUserID(String reportUserID) {
        this.reportUserID = reportUserID;
    }

    public String getReportIP() {
        return reportIP;
    }

    public void setReportIP(String reportIP) {
        this.reportIP = reportIP;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportReplyComment() {
        return reportReplyComment;
    }

    public void setReportReplyComment(String reportReplyComment) {
        this.reportReplyComment = reportReplyComment;
    }

    public String getReportUserFullName() {
        return reportUserFullName;
    }

    public void setReportUserFullName(String reportUserFullName) {
        this.reportUserFullName = reportUserFullName;
    }

    public String getReportUserEmailID() {
        return reportUserEmailID;
    }

    public void setReportUserEmailID(String reportUserEmailID) {
        this.reportUserEmailID = reportUserEmailID;
    }

    public String getFlagTitle() {
        return flagTitle;
    }

    public void setFlagTitle(String flagTitle) {
        this.flagTitle = flagTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReportItemName() {
        return reportItemName;
    }

    public void setReportItemName(String reportItemName) {
        this.reportItemName = reportItemName;
    }
}
