package com.paya.paragon.api.propertyLocation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PropertyLocationApi {
    @FormUrlEncoded
    @POST("dashboard/getPropertyLocation/")
    Call<PropertyLocationResponse> post(
            @Field("languageID") String languageID,
            @Field("location") String keyword
    );
}
