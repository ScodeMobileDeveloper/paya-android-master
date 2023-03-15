package com.paya.paragon.api.PostPropertyRegion;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostPropertyRegion1Api {
    @FormUrlEncoded
    @POST("property/PostPropertyRegionforReq/")
    Call<PropertyRegionResponse> post(
            @Field("languageID") String languageID,
            @Field("searchText") String keyword,
            @Field("location") String location
    );
}
