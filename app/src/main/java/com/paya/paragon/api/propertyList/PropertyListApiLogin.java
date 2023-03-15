package com.paya.paragon.api.propertyList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PropertyListApiLogin {
    @FormUrlEncoded
    @POST("list")
    Call<PropertyListResponse> post(
            @Field("languageID") String languageID,
            @Field("itemType") String itemType,
            @Field("searchPropertyPurpose") String searchPropertyPurpose,
            @Field("cityID") String cityID,
            @Field("searchPropertyTypeID") String searchPropertyTypeID,
            @Field("searchPropertyBedRoom") String searchPropertyBedRoom,
            @Field("searchMinPrice") String searchMinPrice,
            @Field("searchMaxPrice") String searchMaxPrice,
            @Field("countryID") String countryID,
            @Field("userID") String userID,
            @Field("page") String page
    );
}
