package com.paya.paragon.api.statisticsList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface StatisticsListApi {
    @FormUrlEncoded
    @POST("statistics")
    Call<StatisticsListResponse> post(
            @Field("userID") String userID,
            @Field("action") String action,
            @Field("serDate") String serDate,
            @Field("serPropID") String serPropID

    );
}
