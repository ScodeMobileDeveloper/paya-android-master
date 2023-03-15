package com.paya.paragon.api.index;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IndexBuyRentApi {
    @FormUrlEncoded
    @POST("index/commonListing")
    Call<IndexBuyRentResponse> post(
            @Field("purpose") String purpose,
            @Field("countryID") String countryID,
            @Field("languageID") String languageID
    );
}
