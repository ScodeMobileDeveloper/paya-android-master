package com.paya.paragon.api.AgentCityList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BuilderCityListingApi {
    @FormUrlEncoded
    @POST("builders/cityLoad")
    Call<AgentCityListingResponse> post(
            @Field("countryID") String countryID
    );
}
