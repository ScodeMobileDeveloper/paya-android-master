package com.paya.paragon.api.ListYourBusinessSubmit;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ListYourBusinessApi {
    @FormUrlEncoded
    @POST("localexpert/enquiry/")
    Call<ListYourBusinessResponse> post(
            @Field("businessReqName") String businessReqName,
            @Field("businessReqCategoryID") String businessReqCategoryID,
            @Field("businessReqEmail") String businessReqEmail,
            @Field("countryID") String countryID,
            @Field("businessReqFirstName") String businessReqFirstName,
            @Field("businessReqAddress1") String businessReqAddress1,
            @Field("locCity") String locCity,
            @Field("businessReqPhone") String businessReqPhone,
            @Field("businessReqMobile") String businessReqMobile,
            @Field("enquiryPage") String enquiryPage,
            @Field("action") String action,
            @Field("businessCountrycode") String businessCountrycode
    );
}
