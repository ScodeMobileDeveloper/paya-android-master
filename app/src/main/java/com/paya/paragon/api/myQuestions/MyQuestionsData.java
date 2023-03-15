package com.paya.paragon.api.myQuestions;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class MyQuestionsData {
    @SerializedName("questionID") @Expose private String questionID;
    @SerializedName("questionDate") @Expose private String questionDate;
    @SerializedName("questionTitle") @Expose private String questionTitle;
    @SerializedName("questionCategoryName") @Expose private String questionCategoryName;
    @SerializedName("questionAddedBy") @Expose private String questionAddedBy;
    @SerializedName("questionAddedType") @Expose private String questionAddedType;
    @SerializedName("likeCount") @Expose private String likeCount;
    @SerializedName("unlikeCount") @Expose private String unlikeCount;
    @SerializedName("answerCount") @Expose private String answerCount;
    @SerializedName("questionPostedBy") @Expose private String questionPostedBy;
    @SerializedName("userProfilePicture") @Expose private String userProfilePicture;
    @SerializedName("answers") @Expose private ArrayList<MyQuestionAnswerData> answers;

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(String questionDate) {
        this.questionDate = questionDate;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionCategoryName() {
        return questionCategoryName;
    }

    public void setQuestionCategoryName(String questionCategoryName) {
        this.questionCategoryName = questionCategoryName;
    }

    public String getQuestionAddedBy() {
        return questionAddedBy;
    }

    public void setQuestionAddedBy(String questionAddedBy) {
        this.questionAddedBy = questionAddedBy;
    }

    public String getQuestionAddedType() {
        return questionAddedType;
    }

    public void setQuestionAddedType(String questionAddedType) {
        this.questionAddedType = questionAddedType;
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

    public String getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(String answerCount) {
        this.answerCount = answerCount;
    }

    public String getQuestionPostedBy() {
        return questionPostedBy;
    }

    public void setQuestionPostedBy(String questionPostedBy) {
        this.questionPostedBy = questionPostedBy;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public ArrayList<MyQuestionAnswerData> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<MyQuestionAnswerData> answers) {
        this.answers = answers;
    }
}
