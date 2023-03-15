package com.paya.paragon.api.AgentSubLocation;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AgentSubLocApi {
    @FormUrlEncoded
    @POST("agents/getallcitylocationsSub/")
    Call<AgentSubLocResponse> post(
            @Field("cityKey") String cityKey
    );
}
