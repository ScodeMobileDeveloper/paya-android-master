package com.paya.paragon.api.myPropertiesDelete;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteMyPropertiesApi {
    @FormUrlEncoded
    @POST("dashboard/statusChange")
    Call<DeleteMyPropertiesResponse> post(
            @Field("newstatus") String newstatus,
            @Field("propID") String propID
    );
}
