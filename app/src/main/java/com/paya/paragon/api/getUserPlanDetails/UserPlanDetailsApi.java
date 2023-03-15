package com.paya.paragon.api.getUserPlanDetails;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserPlanDetailsApi {
    @FormUrlEncoded
    @POST("loadPlnPayment")
    Call<UserPlanDetailsResponse> post(
            @Field("planID") String planID,
            @Field("userID") String userID
    );
}
