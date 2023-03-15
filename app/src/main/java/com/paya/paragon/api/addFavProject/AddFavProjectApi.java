package com.paya.paragon.api.addFavProject;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddFavProjectApi {
    @FormUrlEncoded
    @POST("property/addFavoriteProject")
    Call<AddFavProjectResponse> post(
            @Field("projectID") String projectID,
            @Field("userID") String userID
    );
}
