package com.paya.paragon.api.builderProjects;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BuildersProjectListApi {
    @FormUrlEncoded
    @POST("builders/builderProjectList")
    Call<BuildersProjectResponse> post(
            @Field("builderUserID") String builderUserID,
            @Field("pageID") String pageID,
            @Field("projCurntSts") String projCurntSts
    );
}
