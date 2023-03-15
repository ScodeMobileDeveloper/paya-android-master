package com.paya.paragon.api.postProperty.communityList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommunityListingApi {
    @FormUrlEncoded
    @POST("getCommunity")
    Call<CommunityListingResponse> post(
            @Field("cityName") String cityName,
            @Field("searchText") String searchText
    );
}
