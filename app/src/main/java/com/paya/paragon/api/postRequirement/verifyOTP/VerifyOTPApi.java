package com.paya.paragon.api.postRequirement.verifyOTP;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VerifyOTPApi {
    @FormUrlEncoded
    @POST("property/checkEnqOtp")
    Call<VerifyOTPResponse> post(
            @Field("enquiryID") String enquiryID,
            @Field("otpText") String otpText,
            @Field("parm") String parm
    );
}
