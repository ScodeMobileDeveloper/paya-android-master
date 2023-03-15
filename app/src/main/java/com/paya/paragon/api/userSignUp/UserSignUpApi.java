package com.paya.paragon.api.userSignUp;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserSignUpApi {
    @FormUrlEncoded
    @POST("login/usersignup")
    Call<UserSignUpResponse> post(
            @Field("userTypeID") String userTypeID,
            @Field("userStatus") String userStatus,
            @Field("individualUserType") String individualUserType,
            @Field("userFirstName") String userFirstName,
            @Field("userLastName") String userLastName,
            @Field("userPhone") String userPhone,
            @Field("countryCode") String countryCode,
            @Field("userEmail") String userEmail,
            @Field("userPassword") String userPassword,
            @Field("countryID") String countryID,
            @Field("userNewsletterSubscribeStatus") String userNewsletterSubscribeStatus,
            @Field("stateSelectedType") String stateSelectedType,
            @Field("languageID") String languageID,
            @Field("fcm_token") String deviceToken,
            @Field("enableOtp") String enableOTP,
            @Field("country_code") String countryCodewithPlus

    );
}
