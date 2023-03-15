package com.paya.paragon.api.postedRequirementsDelete;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostedRequirementsDeleteApi {
    @FormUrlEncoded
    @POST("dashboard/deletePostReq")
    Call<PostedRequirementsDeleteResponse> post(
            @Field("reqID") String reqID,
            @Field("userID") String userID,
            @Field("languageID") String languageID
    );
}
