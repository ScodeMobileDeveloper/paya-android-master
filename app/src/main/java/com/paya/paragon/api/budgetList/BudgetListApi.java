package com.paya.paragon.api.budgetList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BudgetListApi {
    @FormUrlEncoded
    @POST("property/setMinAndMaxPrice")
    Call<BudgetListResponse> post(
            @Field("languageId") String languageId

    );
}
