package com.paya.paragon.api.postedRequirements;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostedRequirementsApi {
    @FormUrlEncoded
    @POST("dashboard/postedRequirementsList")
    Call<PostedRequirementsResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );
}
