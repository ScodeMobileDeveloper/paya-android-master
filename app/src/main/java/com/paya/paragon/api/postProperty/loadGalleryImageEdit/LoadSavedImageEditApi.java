package com.paya.paragon.api.postProperty.loadGalleryImageEdit;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoadSavedImageEditApi {
    @FormUrlEncoded
    @POST("dashboard/loadGalleryImages")
    Call<LoadSavedImageEditResponse> post(
            @Field("propertyID") String propertyID,
            @Field("imageCatID") String imageCatID
    );
}
