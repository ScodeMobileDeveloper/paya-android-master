package com.paya.paragon.api.postRequirement.resendOTP;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ReSendOTPApi {
    @FormUrlEncoded
    @POST("property/resetEnqOTP")
    Call<ReSendOTPResponse> post(
            @Field("enquiryID") String enquiryID,
            @Field("parm") String parm,
            @Field("countryCode") String countryCode
    );
}
