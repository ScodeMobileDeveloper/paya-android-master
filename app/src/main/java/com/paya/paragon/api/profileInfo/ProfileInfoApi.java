package com.paya.paragon.api.profileInfo;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProfileInfoApi {
    @FormUrlEncoded
    @POST("dashboard/getUserInfo")
    Call<ProfileInfoResponse> post(
            @Field("userID") String userID
    );

}
