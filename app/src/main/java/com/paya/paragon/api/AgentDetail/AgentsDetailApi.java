package com.paya.paragon.api.AgentDetail;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AgentsDetailApi {
    @FormUrlEncoded
    @POST("agents/details/")
    Call<AgentDetailResponse> post(
            @Field("agencyKey") String agencyKey,
            @Field("userID") String userID
    );
}
