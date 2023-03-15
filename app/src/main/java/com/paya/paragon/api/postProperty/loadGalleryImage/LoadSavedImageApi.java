package com.paya.paragon.api.postProperty.loadGalleryImage;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoadSavedImageApi {
    @FormUrlEncoded
    @POST("dashboard/loadGalleryImages")
    Call<LoadSavedImageResponse> post(
            @Field("propertyID") String propertyID,
            @Field("imageCatID") String imageCatID
    );
}
