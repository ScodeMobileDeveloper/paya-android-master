package com.paya.paragon.api.postProperty.deleteSavedVideo;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteSavedVideoApi {
    @FormUrlEncoded
    @POST("dashboard/removeVideo")
    Call<DeleteSavedVideoResponse> post(
            @Field("videoID") String videoID
    );
}
