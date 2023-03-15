package com.paya.paragon.api.builderDetails;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BuildersDetailApi {
    @FormUrlEncoded
    @POST("builders/details")
    Call<BuildersDetailResponse> post(
            @Field("builderKey") String builderKey
    );
}
