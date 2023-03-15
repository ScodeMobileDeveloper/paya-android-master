package com.paya.paragon.api.openHouseSlots;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OpenHouseSlotListApi {
    @FormUrlEncoded
    @POST("dashboard/openHouse")
    Call<OpenHouseSlotListResponse> post(
            @Field("action") String action,
            @Field("propertyID") String propertyID,
            @Field("languageID") String languageID
    );
}
