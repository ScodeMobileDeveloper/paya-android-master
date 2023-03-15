package com.paya.paragon.api.localExpertList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LocalExpertListApi {
    @FormUrlEncoded
    @POST("localexpert/expertList/")
    Call<LocalExpertListResponse> post(
            @Field("dealCategoryID") String dealCategoryID,
            @Field("pageId") String pageId,
            @Field("stateID") String stateID,
            @Field("languageID") String languageID
    );
}
