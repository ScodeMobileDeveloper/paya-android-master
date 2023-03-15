package com.paya.paragon.api.listSettings;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ListSettingsApi {
    @FormUrlEncoded
    @POST("dashboard/userSettings")
    Call<ListSettingsResponse> post(
            @Field("userID") String userID
    );
}
