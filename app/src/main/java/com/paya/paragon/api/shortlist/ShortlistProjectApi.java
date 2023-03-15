package com.paya.paragon.api.shortlist;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ShortlistProjectApi {
    @FormUrlEncoded
    @POST("addFavoriteProject")
    Call<ShortlistPropertyResponse> post(
            @Field("projectID") String projectID,
            @Field("userID") String userID
    );
}
