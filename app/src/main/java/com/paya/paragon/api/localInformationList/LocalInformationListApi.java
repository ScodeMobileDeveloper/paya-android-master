package com.paya.paragon.api.localInformationList;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LocalInformationListApi {
    @FormUrlEncoded
    @POST("property/listLocalInfoType")
    Call<LocalInformationListResponse> post(
            @Field("id") String id
    );

}
