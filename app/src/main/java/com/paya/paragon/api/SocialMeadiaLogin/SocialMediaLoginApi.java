package com.paya.paragon.api.SocialMeadiaLogin;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SocialMediaLoginApi {
    @FormUrlEncoded
    @POST("login/facebookLogin")
    Call<SocialMediaResponse> post(
            @Field("email") String email,
            @Field("id") String id,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("loginType") String loginType,
            @Field("gender") String gender,
            @Field("languageID") String languageID
    );
}
