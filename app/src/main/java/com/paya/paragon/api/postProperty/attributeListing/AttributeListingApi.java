package com.paya.paragon.api.postProperty.attributeListing;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AttributeListingApi {
    @FormUrlEncoded
    @POST("dashboard/bindAttributeForm")
    Call<AttributeListingResponse> post(
            @Field("languageID") String languageID,
            @Field("propertyTypeID") String propertyTypeID,
            @Field("propertyID") String propertyID
    );
}
