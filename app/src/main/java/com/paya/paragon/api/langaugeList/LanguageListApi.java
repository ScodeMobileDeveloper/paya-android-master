package com.paya.paragon.api.langaugeList;



import retrofit2.Call;
import retrofit2.http.POST;

public interface LanguageListApi {
    @POST("index/languageList/")
    Call<LanguageListResponse> post(
    );
}
