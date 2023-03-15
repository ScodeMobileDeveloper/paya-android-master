package com.paya.paragon.api.postRequirement.requirementPost;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostRequirementApi {
    @FormUrlEncoded
    @POST("property/postRequirement")
    Call<PostRequirementResponse> post(
            @Field("reqID") String reqID,
            @Field("reqPurpose") String reqPurpose,
            @Field("propertyMainTypeID") String propertyMainTypeID,
            @Field("propertyTypeID") String propertyTypeID,
            @Field("reqMinPriceBuy") String reqMinPriceBuy,
            @Field("reqMaxPriceBuy") String reqMaxPriceBuy,
            @Field("reqMinPriceRent") String reqMinPriceRent,
            @Field("reqMaxPriceRent") String reqMaxPriceRent,
            @Field("googleSearchText") String googleSearchText,
            @Field("googleLocality") String googleLocality,
            @Field("googleCityName") String googleCityName,
            @Field("googleStateName") String googleStateName,
            @Field("googleCountryName") String googleCountryName,
            @Field("propertyLat") String propertyLat,
            @Field("propertyLong") String propertyLong,
            @Field("attributeList") String attributeList,
            @Field("reqName") String reqName,
            @Field("reqEmail") String reqEmail,
            @Field("reqPhone") String reqPhone,
            @Field("otpEnqIDReq") String otpEnqIDReq,
            @Field("otpTextReq") String otpTextReq,
            @Field("userID") String userID,
            @Field("countryCode") String countryCode
    );
}
