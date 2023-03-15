package com.paya.paragon.api.contactMerchantLocalExpert;

import com.paya.paragon.api.getProfileDetails.BaseResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DownLoadVoucherAPI {
    @FormUrlEncoded
    @POST("localexpert/localexpertEnquiry/")
    Call<BaseResponse>postVoucherDownLoadDetails(@FieldMap HashMap<String,String>data);

}
