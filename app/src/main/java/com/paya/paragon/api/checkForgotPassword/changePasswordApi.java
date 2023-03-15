package com.paya.paragon.api.checkForgotPassword;

import com.paya.paragon.api.getProfileDetails.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface changePasswordApi {
    @FormUrlEncoded
    @POST("login/changePassword")
    Call<BaseResponse> postPassword(@Field("userPhone") String userPhone,
                                    @Field("userNewPassword") String userNewPassword,
                                    @Field("userConfirmPassword") String userConfirmPassword,
                                    @Field("languageID") String languageID


    );
}
