package com.paya.paragon.api.projectDetails;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProjectDetailsApiLogin {
    @FormUrlEncoded
    @POST("projectDetails")
    Call<ProjectDetailsResponse> post(
            @Field("projectID") String projectID,
            @Field("userID") String userID
    );
}
