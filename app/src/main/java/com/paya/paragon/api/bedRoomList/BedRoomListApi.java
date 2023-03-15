package com.paya.paragon.api.bedRoomList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BedRoomListApi {
    @FormUrlEncoded
    @POST("index/bindPropertyBedroom")
    Call<BedRoomListResponse> post(
            @Field("typeID") String typeID,
                @Field("languageID") String languageID
    );
}
