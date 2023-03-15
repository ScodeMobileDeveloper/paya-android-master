package com.paya.paragon.api.listLocProject

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ListLocProjApi {
    @FormUrlEncoded
    @POST("index/listRegion")
    fun post(
        @Field("countryID") countryID: String?,
        @Field("keyword") keyword: String?,
        @Field("selectedCity") selectedCity: String?,
        @Field("location") location: String?
           /* @Field("cityID") cityID: String?,
            @Field("languageID") languageID: String?,
            @Field("keyword") keyword: String?,*/

    ): Call<ListLocProjectResponse>
}