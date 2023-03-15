package com.paya.paragon.api.viewingRequests;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ViewingRequestApi {
    @FormUrlEncoded
    @POST("dashboard/scheduleList")
    Call<ViewingRequestResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );
}
