package com.paya.paragon.api

import com.paya.paragon.model.GenericResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DeleteUserApi {

    @POST("user/deactivateUser")
    @FormUrlEncoded
    fun  deleteUser(
        @Field("userID") userID: String,
    ): Call<GenericResponse>

}