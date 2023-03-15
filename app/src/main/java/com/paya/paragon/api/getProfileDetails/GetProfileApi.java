package com.paya.paragon.api.getProfileDetails;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetProfileApi {
    @FormUrlEncoded
    @POST("dashboard/editProfile")
    Call<GetProfileResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );

}
