package com.paya.paragon.api.RequirementAttributeListing;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequirementAttributeListingApi {
    @FormUrlEncoded
    @POST("property/bindAttributeFormEnquiry")
    Call<RequirementAttributeListingResponse> post(
            @Field("propertyTypeID") String propertyTypeID
    );
}
