package com.paya.paragon.api.postProperty.PostPropertyLocalityList;

import com.paya.paragon.api.postProperty.PostPropertyLocalityList.city.CityResponse;
import com.paya.paragon.api.postProperty.PostPropertyLocalityList.state.StateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface StateListAPI {
    @FormUrlEncoded
    @POST("index/stateList")
    Call<StateResponse> getState(
            @Field("languageID") String languageID,
            @Field("countryID") String countryID
    );
    @FormUrlEncoded
    @POST("index/cityList")
    Call<CityResponse> getCity(
            @Field("languageID") String languageID,
            @Field("stateID") String stateID
    );
}
