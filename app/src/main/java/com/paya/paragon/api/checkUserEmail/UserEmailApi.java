package com.paya.paragon.api.checkUserEmail;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserEmailApi {
    @FormUrlEncoded
    @POST("checkEmail")
    Call<UserEmailResponse> post(
            @Field("userLoginEmail") String userLoginEmail
    );
}
