package com.paya.paragon.api.upgradePlanApi;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UpgradePlanApi {
    @FormUrlEncoded
    @POST("dashboard/loadPlanPayment/")
    Call<UpgradePlanResponse> post(
            @Field("userID") String userID,
            @Field("planID") String planID,
            @Field("languageID") String languageID
    );

}
