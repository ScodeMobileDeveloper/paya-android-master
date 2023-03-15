package com.paya.paragon.api.deleteShortlistedProperty;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteShortlistedPropertyApi {
    @FormUrlEncoded
    @POST("dashboard/unFavourProp")
    Call<DeleteShortlistedPropertyResponse> post(
            @Field("propertyID") String propertyID,
            @Field("userID") String userID
    );
}
