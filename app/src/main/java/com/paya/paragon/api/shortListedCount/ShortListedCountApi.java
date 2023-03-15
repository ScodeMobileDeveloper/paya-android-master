package com.paya.paragon.api.shortListedCount;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ShortListedCountApi {
    @FormUrlEncoded
    @POST("dashboard/shortListedCount/")
    Call<ShortListedCountResponse> post(
            @Field("userID") String userID
    );
}
