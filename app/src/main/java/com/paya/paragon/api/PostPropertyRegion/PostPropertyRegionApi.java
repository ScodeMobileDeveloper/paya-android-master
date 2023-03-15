package com.paya.paragon.api.PostPropertyRegion;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostPropertyRegionApi {
    @FormUrlEncoded
    @POST("dashboard/PostPropertyRegion/")
    Call<PropertyRegionResponse> post(
            @Field("languageID") String languageID,
            @Field("searchText") String keyword
    );
}
