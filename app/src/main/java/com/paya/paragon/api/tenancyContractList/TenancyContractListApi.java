package com.paya.paragon.api.tenancyContractList;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TenancyContractListApi {
    @FormUrlEncoded
    @POST("dashboard/myTenancy")
    Call<TenancyContractListResponse> post(
            @Field("userID") String userID,
            @Field("languageID") String languageID

    );
}
