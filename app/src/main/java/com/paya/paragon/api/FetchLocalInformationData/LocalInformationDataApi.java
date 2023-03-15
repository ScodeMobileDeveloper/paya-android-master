package com.paya.paragon.api.FetchLocalInformationData;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LocalInformationDataApi {
    @FormUrlEncoded
    @POST("property/localInfoFetch")
    Call<LocalInformationDataResponse> post(
            @Field("typeId") String typeId,
            @Field("locationLat") String locationLat,
            @Field("locationLng") String locationLng
    );

}
