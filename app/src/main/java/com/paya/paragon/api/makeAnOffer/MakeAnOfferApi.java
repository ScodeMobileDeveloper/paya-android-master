package com.paya.paragon.api.makeAnOffer;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MakeAnOfferApi {
    @FormUrlEncoded
    @POST("property/propertyOffer/")
    Call<MakeAnOfferResponse> post(
            @Field("enquiryName") String enquiryName,
            @Field("enquiryEmail") String enquiryEmail,
            @Field("isOffer") String isOffer,
            @Field("userOfferPrice") String userOfferPrice,
            @Field("enquiryPhone") String enquiryPhone,
            @Field("countryCode") String countryCode,
            @Field("enquiryContent") String enquiryContent,
            @Field("propertyID") String propertyID,
            @Field("mortgage") String mortgage
    );
}
