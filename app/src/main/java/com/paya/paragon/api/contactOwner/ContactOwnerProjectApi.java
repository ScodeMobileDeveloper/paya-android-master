package com.paya.paragon.api.contactOwner;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ContactOwnerProjectApi {
    @FormUrlEncoded
    @POST("property/projectEnquiry")
    Call<ContactOwnerResponse> post(
            @Field("userID") String userID,
            @Field("enquiryTypeID") String enquiryTypeID,
            @Field("enquiryType") String enquiryType,
            @Field("enquiryPage") String enquiryPage,
            @Field("enquiryEmail") String enquiryEmail,
            @Field("enquiryName") String enquiryName,
            @Field("enquiryPhone") String enquiryPhone,
            @Field("countryCode") String countryCode
    );
}
