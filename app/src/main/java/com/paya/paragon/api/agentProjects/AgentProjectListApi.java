package com.paya.paragon.api.agentProjects;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AgentProjectListApi {
    @FormUrlEncoded
    @POST("agents/agencyProjectList/")
    Call<AgentProjectResponse> post(
            @Field("agencyUserID") String agencyUserID,
            @Field("page") String page
    );
}
