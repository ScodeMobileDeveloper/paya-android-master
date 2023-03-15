package com.paya.paragon.api.agentList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SubAgentDataListAPI {

    @FormUrlEncoded
    @POST("Agents/getSubAgentData")
    Call<AgentDataListResponse> post(
            @Field("limitFrom") String limitFrom,
            @Field("limitTo") String limitTo,
            @Field("orderby") String orderby,
            @Field("keywords") String keywords,
            @Field("languageIDs") String locationIDs,
            @Field("userOwnerID") String mainAgentId
    );

}
