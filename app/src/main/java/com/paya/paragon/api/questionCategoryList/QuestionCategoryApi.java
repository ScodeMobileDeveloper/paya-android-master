package com.paya.paragon.api.questionCategoryList;


import retrofit2.Call;
import retrofit2.http.POST;

public interface QuestionCategoryApi {
    @POST("dashboard/questionCategoryList")
    Call<QuestionCategoryResponse> post();
}
