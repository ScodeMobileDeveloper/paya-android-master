package com.paya.paragon.api.projectDetails;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProjectDetails1Api {
    @FormUrlEncoded
    @POST("property/projectDetails/")
    Call<ProjectDetails1Response> post(
            @Field("projectID") String projectID,
            @Field("languageID") String languageID,
            @Field("userID") String userID
    );
}
