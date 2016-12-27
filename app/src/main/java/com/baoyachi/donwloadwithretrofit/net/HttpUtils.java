package com.baoyachi.donwloadwithretrofit.net;

import com.baoyachi.donwloadwithretrofit.interceptor.ProgressInterceptor;
import com.baoyachi.donwloadwithretrofit.ProgressListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 日期：16/3/17 20:41
 * <p>
 * 描述：
 * 修复：
 */
public class HttpUtils
{
    public static ApiService createService(ProgressListener progressListener)
    {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()//
                .readTimeout(5, TimeUnit.SECONDS)//
                .connectTimeout(5, TimeUnit.SECONDS)//
                .addInterceptor(new HttpLoggingInterceptor()//
                        .setLevel(HttpLoggingInterceptor.Level.BODY))//
                .addInterceptor(new ProgressInterceptor(progressListener)).build();

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()//
                .baseUrl("https://www.wandoujia.com/")//
                .client(okHttpClient)//
                .addConverterFactory(GsonConverterFactory.create(gson))//
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        return apiService;
    }


}
