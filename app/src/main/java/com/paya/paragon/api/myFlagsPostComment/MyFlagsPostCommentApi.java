package com.paya.paragon.api.myFlagsPostComment;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyFlagsPostCommentApi {
    @FormUrlEncoded
    @POST("flagCommentSubmit")
    Call<MyFlagsPostCommentResponse> post(
            @Field("reportID") String reportID,
            @Field("flagComment") String flagComment,
            @Field("flagType") String flagType
    );
}
