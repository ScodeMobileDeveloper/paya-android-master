package com.paya.paragon.api.bankListPropertyPost;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BankListPropertyApi {
    @FormUrlEncoded
    @POST("dashboard/bankListPropertyPost/")
    Call<BankListPropertyResponse> post(
            @Field("languageID") String languageID
    );
}
