package com.paya.paragon.api.getProfileState;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetProfileStateApi {
    @FormUrlEncoded
    @POST("states")
    Call<GetProfileStateResponse> post(
            @Field("countryID") String countryID
    );

}
