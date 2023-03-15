package com.paya.paragon.api.profilePhoneUpdate;

import com.paya.paragon.api.postRequirement.verifyOTP.VerifyOTPResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProfilePhoneNoUpdate {
    @FormUrlEncoded
    @POST("login/verifyOtpUpdatePhone/")
    Call<VerifyOTPResponse> post(
            @Field("otpText") String otpText,
            @Field("userID") String userID,
            @Field("country_code") String countryCode,
            @Field("userPhone") String userPhone
    );
}
