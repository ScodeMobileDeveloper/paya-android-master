package com.paya.paragon.api.shoertlistedProperties;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ShortlistedPropertyListApi {
    @FormUrlEncoded
    @POST("dashboard/favouriteProperties")
    Call<ShortlistedPropertyListResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );
}
