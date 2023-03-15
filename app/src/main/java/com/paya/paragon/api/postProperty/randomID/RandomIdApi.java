package com.paya.paragon.api.postProperty.randomID;



import com.paya.paragon.api.StandardResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RandomIdApi {
    @FormUrlEncoded
    @POST("dashboard/addProperty")
    Call<RandomIdResponse> post(
            @Field("languageID") String languageID,
            @Field("action") String action,
            @Field("userID") String userID
    );

    @FormUrlEncoded
    @POST("dashboard/checkPlanExpiry")
    Call<StandardResponse> checkLimitReached(
            @Field("userID") String userID
    );
}
