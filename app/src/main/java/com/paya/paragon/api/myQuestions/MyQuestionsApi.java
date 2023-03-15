package com.paya.paragon.api.myQuestions;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyQuestionsApi {
    @FormUrlEncoded
    @POST("dashboard/loadQuestionAnswer")
    Call<MyQuestionsResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );
}
