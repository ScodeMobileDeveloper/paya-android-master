package com.paya.paragon.api.agentProperty;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AgentPropertyListApi {
    @FormUrlEncoded
    @POST("agents/propertyList/")
    Call<AgentPropertyResponse> post(
            @Field("agencyUserID") String agencyUserID,
            @Field("purpose") String purpose,
            @Field("page") String page
    );
}
