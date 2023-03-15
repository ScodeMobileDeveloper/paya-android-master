package com.paya.paragon.api.saveEditRequirement;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SaveEditRequirementApi {
    @FormUrlEncoded
    @POST("editPostReq")
    Call<SaveEditRequirementResponse> post(
            @Field("userID") String userID,
            @Field("action") String action,
            @Field("reqID") String reqID,
            @Field("reqPurpose") String reqPurpose,
            @Field("reqName") String reqName,
            @Field("reqEmail") String reqEmail,
            @Field("reqPhone") String reqPhone,
            @Field("countryCode") String countryCode,
            @Field("locationList") String locationList,
            @Field("propertyTypeID") String propertyTypeID,
            @Field("reqBedroom") String reqBedroom,
            @Field("minPrice") String minPrice,
            @Field("maxPrice") String maxPrice
    );
}
