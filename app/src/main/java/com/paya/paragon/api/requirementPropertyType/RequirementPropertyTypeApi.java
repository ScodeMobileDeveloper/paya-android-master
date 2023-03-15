package com.paya.paragon.api.requirementPropertyType;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RequirementPropertyTypeApi {
    @FormUrlEncoded
    @POST("property/propertyTypesRequirement")
    Call<RequirementPropertyTypeResponse> post(
            @Field("languageID") String languageID);
}
