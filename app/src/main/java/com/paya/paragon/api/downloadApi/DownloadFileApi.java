package com.paya.paragon.api.downloadApi;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DownloadFileApi {
    @GET
    Call<ResponseBody> downloadFile(@Url String url);

}
