package com.paya.paragon.api.postProperty.postPropertyIndex;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostPropertyIndexApi {
    @FormUrlEncoded
    @POST("dashboard/PostPropertyList")
    Call<PostPropertyIndexResponse> post(
            @Field("languageID") String languageID
    );
}
