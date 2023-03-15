package com.paya.paragon.api.addOpenHouseSlot;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddOpenHouseSlotApi {
    @FormUrlEncoded
    @POST("dashboard/openHouse")
    Call<AddOpenHouseSlotResponse> post(
            @Field("action") String action,
            @Field("StartDateTimeNew") String StartDateTimeNew,
            @Field("EndDateTimeNew") String EndDateTimeNew,
            @Field("propertyID") String propertyID,
            @Field("slotID") String slotID
    );
}
