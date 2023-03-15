package com.paya.paragon.api.postProperty.locationDetails;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LocationDetailsApi {
    @FormUrlEncoded
    @POST("getLocationDetails")
    Call<LocationDetailsResponse> post(
            @Field("locationName") String locationName,
            @Field("subcommunity") String subcommunity
    );
}
