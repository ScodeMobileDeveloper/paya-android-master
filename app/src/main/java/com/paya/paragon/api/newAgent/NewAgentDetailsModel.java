package com.paya.paragon.api.newAgent;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NewAgentDetailsModel {
    @FormUrlEncoded
    @POST("Agents/AgentDetails")
    Call<NewAgentDetailsResponseModel> post(
            @Field("userid") String userId,
            @Field("languageIDs") String languageId);


}
