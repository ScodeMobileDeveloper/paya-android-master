package com.paya.paragon.api.postRequirement.editPostRequirement;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EditPostedRequirementsApi {
    @FormUrlEncoded
    @POST("property/editPostReqNew")
    Call<EditPostedRequirementsResponse> post(
            @Field("userID") String userID,
            @Field("reqID") String reqID
    );
}
