package com.paya.paragon.api.agentSignUp;

import com.paya.paragon.api.userSignUp.UserSignUpResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AgentSignUpApi {
    @Multipart
    @POST("login/usersignup")
    Call<UserSignUpResponse> post(
            @Part("userTypeID") RequestBody userTypeID,
            @Part("userStatus") RequestBody userStatus,
            @Part("userCompanyName") RequestBody userCompanyName,
            @Part("userFirstName") RequestBody userFullName,
            @Part("userPhone") RequestBody userPhone,
            @Part("userPhone2") RequestBody userPhone2,
            @Part("countryCode") RequestBody countryCode,
            @Part("userPassword") RequestBody userPassword,
            @Part("userLocation") RequestBody userLocation,
            @Part("userNewsletterSubscribeStatus") RequestBody userNewsletterSubscribeStatus,
            @Part("languageID") RequestBody languageID,
            @Part("fcm_token") RequestBody deviceToken,
            @Part("enableOtp") RequestBody enableOTP,
            @Part("country_code") RequestBody countryCodewithPlus,
            @Part MultipartBody.Part userCompanyLogo
    );
}
