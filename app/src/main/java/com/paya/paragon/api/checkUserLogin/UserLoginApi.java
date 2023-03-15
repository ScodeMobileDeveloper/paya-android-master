package com.paya.paragon.api.checkUserLogin;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserLoginApi {
    /*@FormUrlEncoded
    @POST("login/checkUserLogin")
    Call<UserLoginResponse> post(
            @Field("userLoginEmail") String userLoginEmail,
            @Field("userLoginPassword") String userLoginPassword,
            @Field("languageID") String languageID,
            @Field("fcm_token") String deviceToken
    );*/

    @FormUrlEncoded
    @POST("login/checkUserLoginByPhone")
    Call<UserLoginResponse> post(
            @Field("userPhone") String userLoginEmail,
            @Field("userLoginPassword") String userLoginPassword,
            @Field("languageID") String languageID,
            @Field("fcm_token") String deviceToken
    );
}
