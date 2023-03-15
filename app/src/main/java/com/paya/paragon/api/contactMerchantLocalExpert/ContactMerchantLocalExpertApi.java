package com.paya.paragon.api.contactMerchantLocalExpert;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ContactMerchantLocalExpertApi {
    @FormUrlEncoded
    @POST("localexpert/localexpertEnquiry/")
    Call<ContactMerchantLocalExpertResponse> post(
            @Field("contactName") String contactName,
            @Field("contactEmail") String contactEmail,
            @Field("contactPhno") String contactPhno,
            @Field("contactEnquiry") String contactEnquiry,
            @Field("businessID") String businessID,
            @Field("enquiryType") String enquiryType,
            @Field("userID") String userID,
            @Field("countryCode") String countryCode
    );
}
