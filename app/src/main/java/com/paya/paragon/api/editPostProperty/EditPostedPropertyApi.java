package com.paya.paragon.api.editPostProperty;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EditPostedPropertyApi {
    @FormUrlEncoded
    @POST("dashboard/editProperty")
    Call<EditPostedPropertyResponse> post(
            @Field("languageID") String languageID,
            @Field("propertyID") String propertyID,
            @Field("userID") String userID);
}
