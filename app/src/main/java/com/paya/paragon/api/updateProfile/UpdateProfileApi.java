package com.paya.paragon.api.updateProfile;



import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UpdateProfileApi {
    @Multipart
    @POST("dashboard/updateProfile")
    Call<UpdateProfileResponse> post(
            @Part("userID") RequestBody userID,
            @Part("userFirstName") RequestBody userFirstName,
            @Part("userLastName") RequestBody userLastName,
            @Part("userGender") RequestBody userGender,
            @Part("userPhone") RequestBody userPhone,
            @Part("googleCountryName") RequestBody googleCountryName,
            @Part("googleStateName") RequestBody googleStateName,
            @Part("googleCityName") RequestBody googleCityName,
            @Part("googleLocality") RequestBody googleLocality,
            @Part("countryCode") RequestBody countryCode,
            @Part("userAddress1") RequestBody userAddress1,
            @Part("userAddress2") RequestBody userAddress2,
            @Part("userEmail") RequestBody userEmail,
            @Part("userZip") RequestBody userZip,
            @Part MultipartBody.Part userProfilePicture,
            @Part("languageID") String languageID
    );

}
