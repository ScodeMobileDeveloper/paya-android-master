package com.paya.paragon.api.saveSearch;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SaveSearchApi {
    @FormUrlEncoded
    @POST("dashboard/saveSearch")
    Call<SaveSearchResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID,
            @Field("languageKey") String languageKey,
            @Field("searchCountryID") String searchCountryID,
            @Field("searchCountryKey") String searchCountryKey,
            @Field("searchPropertyPurpose") String searchPropertyPurpose,
            @Field("searchPropertyAdded") String searchPropertyAdded,
            @Field("searchKeyword") String searchKeyword,
            @Field("searchRegion") String searchRegion,
            @Field("searchMinPrice") String searchMinPrice,
            @Field("searchMaxPrice") String searchMaxPrice,
            @Field("searchPropertyTypeID") String searchPropertyTypeID,
            @Field("searchAttributesStr") String searchAttributesStr,
            @Field("searchType") String searchType,
            @Field("cityID") String cityID,
            @Field("searchTitle") String searchTitle
    );
}
