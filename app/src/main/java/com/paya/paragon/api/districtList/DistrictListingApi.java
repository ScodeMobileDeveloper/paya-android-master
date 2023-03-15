package com.paya.paragon.api.districtList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DistrictListingApi {
    @FormUrlEncoded
    @POST("dashboard/loadDistrict")
    Call<DistrictListingResponse> post(
            @Field("stateID") String stateID
    );
}
