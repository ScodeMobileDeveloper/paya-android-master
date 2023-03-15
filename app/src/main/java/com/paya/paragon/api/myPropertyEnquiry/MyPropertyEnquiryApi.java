package com.paya.paragon.api.myPropertyEnquiry;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyPropertyEnquiryApi {
    @FormUrlEncoded
    @POST("dashboard/propertyEnqList")
    Call<MyPropertyEnquiryResponse> post(
            @Field("propID") String propID,
            @Field("userID") String userID,
            @Field("isOffer") String isOffer
    );
}
