package com.paya.paragon.api.getProfileCity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetProfileCityApi {
    @FormUrlEncoded
    @POST("cityAjaxLoad")
    Call<GetProfileCityResponse> post(
            @Field("stateID") String stateID
    );

}
