package com.paya.paragon.api.shortlist;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ShortlistPropertyApi {
    @FormUrlEncoded
    @POST("addFavoriteProperty")
    Call<ShortlistPropertyResponse> post(
            @Field("propertyID") String propertyID,
            @Field("userID") String userID
    );
}
