package com.paya.paragon.api.updateSettings;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UpdateSettingsApi {
    @FormUrlEncoded
    @POST("dashboard/saveSettings")
    Call<UpdateSettingsResponse> post(
            @Field("languageID") String languageID,
            @Field("userID") String userID,
            @Field("userEmail") String userEmail,
            @Field("userNewsletterSubscribeStatus") String userNewsletterSubscribeStatus,
            @Field("userSmsAlerts") String userSmsAlerts,
            @Field("userPropertyAlerts") String userPropertyAlerts,
            @Field("userPropertyAlertType") String userPropertyAlertType
    );
}
