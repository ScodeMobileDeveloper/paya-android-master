package com.paya.paragon.api.mySavedSearchDelete;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SavedSearchDeleteApi {
    @FormUrlEncoded
    @POST("dashboard/savedSearchDelete")
    Call<SavedSearchDeleteResponse> post(
            @Field("searchID") String searchID,
            @Field("userID") String userID
    );
}
