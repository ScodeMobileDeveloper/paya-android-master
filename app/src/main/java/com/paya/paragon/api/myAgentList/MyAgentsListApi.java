package com.paya.paragon.api.myAgentList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyAgentsListApi {
    @FormUrlEncoded
    @POST("loadFlagList")
    Call<MyAgentsResponse> post(
            @Field("flagType") String flagType,
            @Field("userID") String userID
    );
}
