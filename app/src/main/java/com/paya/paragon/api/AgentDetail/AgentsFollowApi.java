package com.paya.paragon.api.AgentDetail;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AgentsFollowApi {
    @FormUrlEncoded
    @POST("agents/followUser/")
    Call<AgentFollowResponse> post(
            @Field("userID") String userID,
            @Field("propertyUserID") String propertyUserID
    );
}
