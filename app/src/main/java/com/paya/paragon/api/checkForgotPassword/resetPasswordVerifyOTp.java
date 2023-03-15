package com.paya.paragon.api.checkForgotPassword;

import com.paya.paragon.api.postRequirement.verifyOTP.VerifyOTPResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface resetPasswordVerifyOTp {

    @FormUrlEncoded
    @POST("login/otpVerifyForReset")
    Call<VerifyOTPResponse> post(
            @Field("otpText") String otpText,
            @Field("userPhone") String userID,
            @Field("country_code") String countryCode);

}
