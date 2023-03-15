package com.paya.paragon.api.checkForgotPassword;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ForgotPasswordApi {
    @FormUrlEncoded
    @POST("login/resetPassword")
    Call<ForgotPasswordResponse> post(
            @Field("emailID") String emailID,
            @Field("userTypeID") String userTypeID,
            @Field("languageID") String languageID
    );
}
