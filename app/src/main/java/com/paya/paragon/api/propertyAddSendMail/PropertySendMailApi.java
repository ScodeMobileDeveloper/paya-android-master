package com.paya.paragon.api.propertyAddSendMail;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PropertySendMailApi {
    @FormUrlEncoded
    @POST("dashboard/propertyAddSendMail/")
    Call<PropertySendMailResponse> post(
            @Field("propertyID") String propertyID,
            @Field("userID") String userID,
            @Field("propertyEdit") String propertyEdit

    );
}
