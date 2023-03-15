package com.paya.paragon.api.postProperty.loadVideo;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoadSavedVideoApi {
    @FormUrlEncoded
    @POST("dashboard/loadVideos")
    Call<LoadSavedVideoResponse> post(
            @Field("propertyID") String propertyID
    );
}
