package com.paya.paragon.api.postProperty.saveEditedVideoInfo;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SaveEditedVideoInfoApi {
    @FormUrlEncoded
    @POST("dashboard/updateCustomVideoDetails")
    Call<SaveEditedVideoInfoResponse> post(
            @Field("videoID") String videoID,
            @Field("videoStaus") String videoStaus,
            @Field("propertyVideoTitle") String propertyVideoTitle,
            @Field("propertyVideoDescription") String propertyVideoDescription
    );
}
