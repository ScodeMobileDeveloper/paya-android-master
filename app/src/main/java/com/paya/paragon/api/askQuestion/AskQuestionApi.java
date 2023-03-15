package com.paya.paragon.api.askQuestion;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AskQuestionApi {
    @FormUrlEncoded
    @POST("dashboard/submitQuestion")
    Call<AskQuestionResponse> post(
            @Field("countryID") String countryID,
            @Field("dashbrd") String dashbrd,
            @Field("userID") String userID,
            @Field("questionCategoryID") String questionCategoryID,
            @Field("questionTitle") String questionTitle
    );
}
