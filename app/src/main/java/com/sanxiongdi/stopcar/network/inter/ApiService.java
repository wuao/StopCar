package com.sanxiongdi.stopcar.network.inter;

import com.sanxiongdi.stopcar.uitls.AppNetWorkPrams;

import java.io.IOException;

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
public interface ApiService {

    class Factory {

        private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        private static OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addInterceptor(new ParamsInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();

        public static RandomService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppNetWorkPrams.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient)
                    .build();
            return retrofit.create(RandomService.class);
        }

    }


    class ParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());

            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .build();
            return chain.proceed(newRequest);
        }
    }


}
