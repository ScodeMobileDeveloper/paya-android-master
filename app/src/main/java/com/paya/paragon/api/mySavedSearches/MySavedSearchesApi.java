package com.paya.paragon.api.mySavedSearches;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MySavedSearchesApi {
    @FormUrlEncoded
    @POST("dashboard/savedSearchesList")
    Call<MySavedSearchesResponse> post(
            @Field("languageID") String languageID,
            @Field("userID") String userID,
            @Field("pageID") String pageID
    );
}
