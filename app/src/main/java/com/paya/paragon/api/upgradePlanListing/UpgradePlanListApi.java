package com.paya.paragon.api.upgradePlanListing;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UpgradePlanListApi {
    @FormUrlEncoded
    @POST("dashboard/userPlanList")
    Call<UpgradePlanListResponse> post(
            @Field("userID") String userID
    );
}
