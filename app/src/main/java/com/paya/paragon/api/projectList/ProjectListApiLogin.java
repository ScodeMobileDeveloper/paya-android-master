package com.paya.paragon.api.projectList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProjectListApiLogin {
    @FormUrlEncoded
    @POST("list")
    Call<ProjectListResponse> post(
            @Field("languageID") String languageID,
            @Field("itemType") String itemType,
            @Field("searchPropertyPurpose") String searchPropertyPurpose,
            @Field("cityID") String cityID,
            @Field("searchPropertyTypeID") String searchPropertyTypeID,
            @Field("searchPropertyBedRoom") String searchPropertyBedRoom,
            @Field("searchMinPrice") String searchMinPrice,
            @Field("searchMaxPrice") String searchMaxPrice,
            @Field("countryID") String countryID,
            @Field("userID") String userID,
            @Field("page") String page
    );
}
