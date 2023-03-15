package com.paya.paragon.api.BuyProjects;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BuyProjectsListApi {
    @FormUrlEncoded
    @POST("property/searchList")
    Call<BuyProjectsListResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID,
            @Field("cityID") String cityID,
            @Field("searchPropertyPurpose") String searchPropertyPurpose,
            @Field("page") String page,
            @Field("itemType") String itemType,
            @Field("sortBy") String sortBy,
            @Field("searchMinPrice") String searchMinPrice,
            @Field("searchMaxPrice") String searchMaxPrice,
            @Field("searchPropertyTypeID") String searchPropertyTypeID,
            @Field("searchCountryID") String searchCountryID,
            @Field("searchAttributesStr") String searchAttributesStr,
            @Field("furnished_status") String furnished_status,
            @Field("searchRegion") String searchRegion,
            @Field("possessionStatus") String possessionStatus,
            @Field("searchRadius") String searchRadius,
            @Field("Coordinates") String Coordinates,
            @Field("center") String center,
            @Field("radius") String radius
    );
}
