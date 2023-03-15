package com.paya.paragon.api.cityList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CityListingApi {
    @FormUrlEncoded
    @POST("index/cityList")
    Call<CityListingResponse> post(
            @Field("languageID") String languageID
    );
}
