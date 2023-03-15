package com.paya.paragon.api.listLocProject

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface LocalitiesApi {
    @FormUrlEncoded
    @POST("dashboard/getAllLocalities")
    fun post(

             @Field("cityID") cityID: String?,
             @Field("languageID") languageID: String?,
             @Field("keyword") keyword: String?,

    ): Call<ListLocProjectResponse>
}