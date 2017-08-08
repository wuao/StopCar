package com.sanxiongdi.stopcar.network.inter;

import android.util.Log;

import com.sanxiongdi.stopcar.uitls.AppNetWorkPrams;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lin.woo on 2017/5/3.
 */
public class ApiExecutor {

    private static class SingletonHolder {
        private static final ApiService INSTANCE = create();
    }

    public static final ApiService getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(new ParamsInterceptor())
            .build();

    public static ApiService create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppNetWorkPrams.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient)
                .build();
        return retrofit.create(ApiService.class);
    }

    static class ParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());

            Request newRequest = oldRequest.newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .build();
            Log.d("===", URLDecoder.decode(oldRequest.toString(),"UTF-8"));
            return chain.proceed(newRequest);
        }
    }

}



