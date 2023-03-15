package com.paya.paragon.api.cmsPages;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CmsPagesApi {
    @FormUrlEncoded
    @POST("index/cms/")
    Call<CmsPagesResponse> post(
            @Field("languageID") String languageID,
            @Field("contentID") String contentID
    );
}
