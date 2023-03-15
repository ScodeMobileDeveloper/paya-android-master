package com.paya.paragon.api.localExpertCategory;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LocalExpertCategoryApi {
    @FormUrlEncoded
    @POST("index/localExpertList/")
    Call<LocalExpertCategoryResponse> post(
            @Field("languageID") String languageID
    );
}
