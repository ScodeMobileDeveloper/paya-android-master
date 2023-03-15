package com.paya.paragon.api.propertyDetails;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PropertyDetailsLoginedApi {
    @FormUrlEncoded
    @POST("property/details/")
    Call<PropertyDetailsResponse> post(
            @Field("propertyID") String propertyID,
            @Field("languageID") String languageID,
            @Field("userID") String userID

    );
}
