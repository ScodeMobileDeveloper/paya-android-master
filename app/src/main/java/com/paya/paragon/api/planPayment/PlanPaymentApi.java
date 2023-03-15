package com.paya.paragon.api.planPayment;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PlanPaymentApi {
    @FormUrlEncoded
    @POST("dashboard/planPayment/")
    Call<PlanPaymentResponse> post(
            @Field("userID") String userID,
            @Field("planID") String planID,
            @Field("languageID") String languageID,
            @Field("cityID") String cityID,
            @Field("stateID") String stateID,
            @Field("countryID") String countryID,
            @Field("userPhone") String userPhone,
            @Field("userZip") String userZip,
            @Field("billingFirstName") String billingFirstName,
            @Field("billingLastName") String billingLastName,
            @Field("billingAddress") String billingAddress,
            @Field("billingAddress1") String billingAddress1,
            @Field("billingEmail") String billingEmail,
            @Field("addRewardPoint") String addRewardPoint,
            @Field("rewardPoint") String rewardPoint,
            @Field("paymentSettingsGroupID") String paymentSettingsGroupID,
            @Field("propertyID") String propertyID


    );

}
