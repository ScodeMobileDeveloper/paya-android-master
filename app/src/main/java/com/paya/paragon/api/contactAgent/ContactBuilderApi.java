package com.paya.paragon.api.contactAgent;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ContactBuilderApi {
    @FormUrlEncoded
    @POST("builders/builderEnquiry")
    Call<ContactAgentResponse> post(
            @Field("enquiryName") String enquiryName,
            @Field("enquiryEmail") String enquiryEmail,
            @Field("enquiryPhone") String enquiryPhone,
            @Field("enquiryTypeID") String enquiryTypeID,
            @Field("enquiryType") String enquiryType,
            @Field("countryCode") String countryCode,
            @Field("enquiryContent") String enquiryContent,
            @Field("builderKey") String builderKey
    );
}
