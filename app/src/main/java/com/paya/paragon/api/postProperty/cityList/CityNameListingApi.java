package com.paya.paragon.api.postProperty.cityList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CityNameListingApi {
    @FormUrlEncoded
    @POST("getCity")
    Call<CityNameListingResponse> post(
            @Field("searchCity") String searchCity
    );
}
