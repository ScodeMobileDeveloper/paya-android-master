package com.paya.paragon.api.mySavedSearchStatusChange;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SavedSearchStatusChangeApi {
    @FormUrlEncoded
    @POST("dashboard/savedSearchStatus")
    Call<SavedSearchStatusChangeResponse> post(
            @Field("searchID") String searchID,
            @Field("newstatus") String newstatus,
            @Field("userID") String userID
    );
}
