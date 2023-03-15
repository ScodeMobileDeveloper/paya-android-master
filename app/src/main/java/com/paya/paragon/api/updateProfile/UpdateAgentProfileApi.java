package com.paya.paragon.api.updateProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UpdateAgentProfileApi {
    @Multipart
    @POST("dashboard/updateProfile")
    Call<UpdateProfileResponse> post(
            @Part("userID") RequestBody userID,
            @Part("userCompanyName") RequestBody userCompanyName,
            @Part("userFirstName") RequestBody userFirstName,
            @Part("userPhone") RequestBody userPhone,
            @Part("userPhone2") RequestBody userPhone2,
            @Part("userLocation") RequestBody userLocation,
            @Part("countryCode") RequestBody countryCode,
            @Part MultipartBody.Part userProfilePicture,
            @Part("languageID") String languageID
    );
}
