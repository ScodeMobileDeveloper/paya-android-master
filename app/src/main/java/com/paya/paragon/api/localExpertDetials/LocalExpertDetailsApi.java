package com.paya.paragon.api.localExpertDetials;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LocalExpertDetailsApi {
    @FormUrlEncoded
    @POST("localexpert/details/")
    Call<LocalExpertDetailsResponse> post(
            @Field("businessID") String businessID,
            @Field("languageID") String languageID
    );
}
