package com.paya.paragon.api.agentList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface    AgentDataListAPI {

    @FormUrlEncoded
    @POST("Agents/getAgentData")
    Call<AgentDataListResponse> post(
            @Field("limitFrom") String limitFrom,
            @Field("limitTo") String limitTo,
            @Field("orderby") String orderby,
            @Field("keywords") String keywords,
            @Field("languageIDs") String locationIDs
    );

}
