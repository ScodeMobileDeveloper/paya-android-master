package com.paya.paragon.api.postProperty.subCommunityList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SubCommunityListingApi {
    @FormUrlEncoded
    @POST("getSubcommunites")
    Call<SubCommunityListingResponse> post(
            @Field("community") String community
    );
}
