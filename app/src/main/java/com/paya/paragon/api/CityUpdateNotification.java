package com.paya.paragon.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CityUpdateNotification {

    @FormUrlEncoded
    @POST("Notifications/NotificationData")
    Call<StandardResponse> post(
            @Field("language_id") String languageId,
            @Field("fcm_token") String fcmToken,
            @Field("device_type") String deviceId,
            @Field("cityID") String cityId
    );


}
