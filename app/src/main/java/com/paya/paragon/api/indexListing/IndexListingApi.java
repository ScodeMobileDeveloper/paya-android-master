package com.paya.paragon.api.indexListing;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IndexListingApi {
    @FormUrlEncoded
    @POST("index/indexlisting")
    Call<IndexListingResponse> post(
            @Field("cityID") String cityID,
            @Field("languageID") String languageID
    );
}
