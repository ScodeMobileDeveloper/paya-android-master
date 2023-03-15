package com.paya.paragon.api.getCityListApi;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetCityApi {
    @FormUrlEncoded
    @POST("dashboard/loadDistrict/")
    Call<GetCityResponse> post(
            @Field("stateID") String stateID
    );

}
