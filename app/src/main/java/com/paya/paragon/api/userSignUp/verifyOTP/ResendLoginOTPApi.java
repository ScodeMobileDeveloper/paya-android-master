package com.paya.paragon.api.userSignUp.verifyOTP;



import com.paya.paragon.api.postRequirement.verifyOTP.VerifyOTPResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ResendLoginOTPApi {
    @FormUrlEncoded
    @POST("login/resendOtp")
    Call<VerifyOTPResponse> post(
            @Field("userID") String userID,
            @Field("phone") String phone,
            @Field("country_code") String country_code

    );
}
