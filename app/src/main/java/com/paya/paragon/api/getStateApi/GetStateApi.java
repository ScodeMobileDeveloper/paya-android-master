package com.paya.paragon.api.getStateApi;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetStateApi {
    @FormUrlEncoded
    @POST("index/stateList/")
    Call<GetStateResponse> post(
            @Field("countryID") String countryID,
            @Field("languageID") String languageID
    );

}
