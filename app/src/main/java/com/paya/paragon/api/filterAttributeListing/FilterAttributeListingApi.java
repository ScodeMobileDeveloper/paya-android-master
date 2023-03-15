package com.paya.paragon.api.filterAttributeListing;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FilterAttributeListingApi {
    @FormUrlEncoded
        @POST("property/bindPropertyAttributes")
    Call<FilterAttributeListingResponse> post(
            @Field("typeID") String typeID,
            @Field("languageID") String languageID
    );
}
