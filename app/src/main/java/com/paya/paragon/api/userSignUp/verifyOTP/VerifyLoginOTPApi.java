package com.paya.paragon.api.userSignUp.verifyOTP;



import com.paya.paragon.api.postRequirement.verifyOTP.VerifyOTPResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VerifyLoginOTPApi {
    @FormUrlEncoded
    @POST("login/otpVerify")
    Call<VerifyOTPResponse> post(
            @Field("otpText") String otpText,
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );
}
