package com.paya.paragon.api.agentsFolloing;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AgentsFollowingApi {
    @FormUrlEncoded
    @POST("dashboard/agencyFollowing")
    Call<AgentsFollowingResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID,
            @Field("pageID") String pageID
    );
}
