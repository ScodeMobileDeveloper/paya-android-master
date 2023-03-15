package com.paya.paragon.api.newAgent;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NewAgentPropertyList {

    @FormUrlEncoded
    @POST("Agents/AgentProperties")
    Call<NewAgentDetailsResponseModel> post(
            @Field("userid") String agentId,
            @Field("languageID") String languageId,
            @Field("limitFrom") String limitFrom,
            @Field("limitTo") String limitTo

    );

}
