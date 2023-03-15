package com.paya.paragon.api.listRegion;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ListRegionApi {
    @FormUrlEncoded
    @POST("listRegion")
    Call<ListRegionResponse> post(
            @Field("selectedCity") String selectedCity,
            @Field("interp") String interp,
            @Field("keyword") String keyword
    );
}
