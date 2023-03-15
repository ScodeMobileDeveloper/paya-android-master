package com.paya.paragon.api.offerList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OfferListApi {
    @FormUrlEncoded
    @POST("dashboard/propertyEnqList")
    Call<OfferListResponse> post(
            @Field("isOffer") String isOffer,
            @Field("userID") String userID,
            @Field("propID") String propID,
            @Field("languageID") String languageID
    );
}
