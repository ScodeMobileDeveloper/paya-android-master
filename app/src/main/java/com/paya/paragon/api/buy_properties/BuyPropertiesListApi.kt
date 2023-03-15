package com.paya.paragon.api.buy_properties

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BuyPropertiesListApi {
    @FormUrlEncoded
    @POST("property/searchList")
    fun post(
        @Field("userID") userID: String?,
        @Field("languageID") languageID: String?,
        @Field("cityID") cityID: String?,
        @Field("searchPropertyPurpose") searchPropertyPurpose: String?,
        @Field("page") page: String?,
        @Field("itemType") itemType: String?,
        @Field("sortBy") sortBy: String?,
        @Field("searchMinPrice") searchMinPrice: String?,
        @Field("searchMaxPrice") searchMaxPrice: String?,
        @Field("searchPropertyTypeID") searchPropertyTypeID: String?,
        @Field("searchCountryID") searchCountryID: String?,
        @Field("searchAttributesStr") searchAttributesStr: String?,
        @Field("furnished_status") furnished_status: String?,
        @Field("searchRegion") searchRegion: String?,
        @Field("possessionStatus") possessionStatus: String?,
        @Field("searchRadius") searchRadius: String?,
        @Field("Coordinates") Coordinates: String?,
        @Field("center") center: String?,
        @Field("radius") radius: String?
    ): Call<BuyPropertiesListResponse>
}