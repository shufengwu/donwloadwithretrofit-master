package com.baoyachi.donwloadwithretrofit.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 日期：16/3/17 20:40
 * <p>
 * 描述：
 * 修复：
 */
public interface ApiService
{

    @Streaming
    @GET()
    Call<ResponseBody> downloadAPK(@Url String url);
}
