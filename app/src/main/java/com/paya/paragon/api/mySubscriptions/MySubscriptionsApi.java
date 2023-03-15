package com.paya.paragon.api.mySubscriptions;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MySubscriptionsApi {
    @FormUrlEncoded
    @POST("dashboard/subscriptionList")
    Call<MySubscriptionsResponse> post(
            @Field("languageID") String languageID,
            @Field("userID") String userID
    );
}
