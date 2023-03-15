package com.paya.paragon.api.checkForgotPassword;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface requestOTPApi {
    @FormUrlEncoded
    @POST("login/resetPassword")
    Call<ForgotPasswordResponse> post(
            @Field("userPhone") String userPhone,
            @Field("country_code") String countryCode,
            @Field("languageIDs") String languageID
    );
}
