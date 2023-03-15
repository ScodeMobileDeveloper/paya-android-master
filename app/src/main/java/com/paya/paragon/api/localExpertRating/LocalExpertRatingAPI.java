package com.paya.paragon.api.localExpertRating;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LocalExpertRatingAPI {
    @FormUrlEncoded
    @POST("localexpert/saveExpertReview/")
    Call<LocalExpertRatingResponse> post(
            @Field("businessID") String businessID,
            @Field("currentRating") String currentRating,
            @Field("businessReviewTitle") String businessReviewTitle,
            @Field("businessReviewComment") String businessReviewComment,
            @Field("userID") String userID
    );
}
