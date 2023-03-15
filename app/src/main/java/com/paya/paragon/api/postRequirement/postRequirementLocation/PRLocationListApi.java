package com.paya.paragon.api.postRequirement.postRequirementLocation;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PRLocationListApi {
    @FormUrlEncoded
    @POST("postReqLocation")
    Call<PRLocationListResponse> post(
            @Field("type") String type,
            @Field("keyword") String keyword,
            @Field("interp") String interp
    );
}
