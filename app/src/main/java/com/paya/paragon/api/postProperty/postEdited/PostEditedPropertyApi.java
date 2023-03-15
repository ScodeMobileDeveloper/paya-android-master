package com.paya.paragon.api.postProperty.postEdited;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostEditedPropertyApi {
    @FormUrlEncoded
    @POST("dashboard/addProperty")
    Call<PostEditedPropertyResponse> post(
            @Field("languageID") String languageID,
            @Field("action") String action,
            @Field("propertyID") String randomPropertyID,
            @Field("indProp") String indProp,
            @Field("expired") String expired,
            @Field("propertyPurpose") String propertyPurpose,
            @Field("propertyFeatured") String propertyFeatured,
            @Field("propertyTypeID") String propertyTypeID,
            @Field("propertyName") String propertyName,
            @Field("propertyPrice") String propertyPrice,
            @Field("propertyDescription") String propertyDescription,
            @Field("googleSearchText") String googleSearchText,
            @Field("googleLocality") String googleLocality,
            @Field("googleCityName") String googleCityName,
            @Field("googleStateName") String googleStateName,
            @Field("googleCountryName") String googleCountryName,
            @Field("propertyZipCode") String propertyZipCode,
            @Field("propertyAddress1") String propertyAddress1,
            @Field("propertyAddress2") String propertyAddress2,
            @Field("propertyLatitude") String propertyLatitude,
            @Field("propertyLongitude") String propertyLongitude,
            @Field("propertyCurrentStatus") String propertyCurrentStatus,
            @Field("conMonth") String conMonth,
            @Field("conYear") String conYear,
            @Field("userID") String userID,
            @Field("attributeList") String attributeList,
            @Field("propertyDetailsID") String propertyDetailsID,
            @Field("parentCommunity") String parentCommunity,
            @Field("subCommunity") String subCommunity   ,
            @Field("propertyMortgage") String propertyMortgage,
            @Field("morgBankID") String morgBankID,
            @Field("propertyContactAgent") String propertyContactAgent,
            @Field("propertyAltPhone") String propertyAltPhone,
            @Field("propertyAltEmail") String propertyAltEmail,
            @Field("propertyCheques") String propertyCheques,
            @Field("rentTerm") String rentTerm,
            @Field("threeSixtyView") String threeSixtyView,
            @Field("bluePrintimage") String bluePrintimage,
            @Field("propertyShowPhone") String propertyShowPhone,
            @Field("phoneoneh") String phoneOne,
            @Field("phonetwoh") String phoneTwo,
            @Field("currencyID_1") String currencyID_1 ,
            @Field("propertyPrice_1") String propertyPrice_1,
            @Field("currencyID_5") String currencyID_5,
            @Field("propertyPrice_5") String propertyPrice_5,
            @Field("propertyPricePerM2") String propertyPricePerM2,
            @Field("total_price") String  total_price,
            @Field("countryCodeone") String  countryCodeone,
            @Field("countryCodeTwo") String  countryCodeTwo

    );
}
