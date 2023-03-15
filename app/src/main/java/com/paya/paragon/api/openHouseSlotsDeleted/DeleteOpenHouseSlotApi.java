package com.paya.paragon.api.openHouseSlotsDeleted;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteOpenHouseSlotApi {
    @FormUrlEncoded
    @POST("dashboard/removeSlot")
    Call<DeleteOpenHouseSlotResponse> post(
            @Field("propertyID") String propertyID,
            @Field("slotID") String slotID
    );
}
