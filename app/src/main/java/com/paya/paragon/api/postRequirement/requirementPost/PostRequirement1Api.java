package com.paya.paragon.api.postRequirement.requirementPost;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostRequirement1Api {
    @FormUrlEncoded
    @POST("property/postRequirementNew/")
    Call<PostRequirementResponse> post(
            @Field("languageID") String languageID,
            @Field("reqID") String reqID,
            @Field("reqPurpose") String reqPurpose,
            @Field("propertyTypeID") String propertyTypeID,
            @Field("minPrice") String reqMinPriceBuy,
            @Field("maxPrice") String reqMaxPriceBuy,
            @Field("reqBedroom") String reqBedroom,
            @Field("locationList") String locationList,
            @Field("reqName") String reqName,
            @Field("reqEmail") String reqEmail,
            @Field("reqPhone") String reqPhone,
            @Field("userID") String userID,
            @Field("countryCode") String countryCode,
            @Field("isMortgage") String mortgage
    );
}
