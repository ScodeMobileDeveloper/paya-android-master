package com.paya.paragon.api.propertyDetails;

import com.paya.paragon.api.StandardResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WhatsappClickApi {
    @FormUrlEncoded
    @POST("property/updateWhatsappClicks/")
    Call<StandardResponse> post(
            @Field("propertyID") String propertyID
    );
}
