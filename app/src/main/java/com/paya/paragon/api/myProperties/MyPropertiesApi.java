package com.paya.paragon.api.myProperties;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyPropertiesApi {
    @FormUrlEncoded
    @POST("dashboard/myPropertyList")
    Call<MyPropertiesResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );
}
