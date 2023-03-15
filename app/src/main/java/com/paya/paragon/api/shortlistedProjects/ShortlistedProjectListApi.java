package com.paya.paragon.api.shortlistedProjects;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ShortlistedProjectListApi {
    @FormUrlEncoded
    @POST("dashboard/favouriteProjects")
    Call<ShortlistedProjectListResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );
}
