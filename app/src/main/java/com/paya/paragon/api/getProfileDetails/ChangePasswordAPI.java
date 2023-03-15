package com.paya.paragon.api.getProfileDetails;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface ChangePasswordAPI {
    @FormUrlEncoded
    @POST("dashboard/changePassword")
    Call<BaseResponse> postPassword(@Field("userID") String userID,
                                    @Field("languageID") String languageID,
                                    @Field("userPassword") String userPassword,
                                    @Field("userNewPassword") String userNewPassword,
                                    @Field("userConfirmPassword") String userConfirmPassword


    );
}
