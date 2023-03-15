package com.paya.paragon.api.myPropertyNameList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PropertyNamesApi {
    @FormUrlEncoded
    @POST("dashboard/openHouse")
    Call<PropertyNamesResponse> post(
            @Field("action") String action,
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );
}
