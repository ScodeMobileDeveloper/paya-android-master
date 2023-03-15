package com.paya.paragon.api.myFlags;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyFlagsApi {
    @FormUrlEncoded
    @POST("loadFlagList")
    Call<MyFlagsResponse> post(
            @Field("flagType") String flagType,
            @Field("userID") String userID
    );
}
