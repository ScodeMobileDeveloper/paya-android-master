package com.paya.paragon.api.postProperty.uploadVideoUrl;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UploadVideoUrlApi {
    @FormUrlEncoded
    @POST("dashboard/saveVideo")
    Call<UploadVideoUrlResponse> post(
            @Field("propertyID") String propertyID,
            @Field("videoStaus") String videoStaus,
            @Field("videoType") String videoType,
            @Field("youtubeVideo") String youtubeVideo
    );
}
