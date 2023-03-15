package com.paya.paragon.api.buildersList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BuildersListingApi {
    @FormUrlEncoded
    @POST("builders/builderLists")
    Call<BuildersListingResponse> post(
            @Field("countryID") String countryID,
            @Field("cityKey") String cityKey,
            @Field("pageID") String pageID
    );
}
