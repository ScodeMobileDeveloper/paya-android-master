package com.paya.paragon.api.addFavProperty;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddFavPropertyApi {
    @FormUrlEncoded
    @POST("property/addFavoriteProperty/")
    Call<AddFavPropertyResponse> post(
            @Field("propertyID") String propertyID,
            @Field("userID") String userID,
            @Field("languageIDs") String languageIDs
    );
}
