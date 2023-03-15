package com.paya.paragon.api.getUserPlanDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MyQuestionAnswerData {

    @SerializedName("answerID") @Expose private String answerID;
    @SerializedName("answerDetail") @Expose private String answerDetail;
    @SerializedName("answerDate") @Expose private String answerDate;
    @SerializedName("likeCount") @Expose private String likeCount;
    @SerializedName("unlikeCount") @Expose private String unlikeCount;

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public String getAnswerDetail() {
        return answerDetail;
    }

    public void setAnswerDetail(String answerDetail) {
        this.answerDetail = answerDetail;
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getUnlikeCount() {
        return unlikeCount;
    }

    public void setUnlikeCount(String unlikeCount) {
        this.unlikeCount = unlikeCount;
    }
}
