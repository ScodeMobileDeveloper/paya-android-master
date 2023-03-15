package com.paya.paragon.api.deleteShortlistedProject;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteShortlistedProjectApi {
    @FormUrlEncoded
    @POST("dashboard/unFavourProj")
    Call<DeleteShortlistedProjectResponse> post(
            @Field("projectID") String projectID,
            @Field("userID") String userID
    );
}
