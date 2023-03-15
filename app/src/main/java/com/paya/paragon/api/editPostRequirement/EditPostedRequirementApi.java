package com.paya.paragon.api.editPostRequirement;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EditPostedRequirementApi {
    @FormUrlEncoded
    @POST("editPostReq")
    Call<EditPostedRequirementResponse> post(
            @Field("action") String action,
            @Field("userID") String userID,
            @Field("reqID") String reqID,
            @Field("cityID") String cityID
    );
}
