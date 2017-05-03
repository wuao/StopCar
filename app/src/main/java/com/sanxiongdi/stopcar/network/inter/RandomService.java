package com.sanxiongdi.stopcar.network.inter;

import com.sanxiongdi.stopcar.entity.RandomNumberEntity;


import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wuaomall@gmail.com on 2017/4/28.
 */

public interface  RandomService {
    @Headers({"Content-type:application/x-www-form-urlencoded"})
    @POST("api/rest")
    Observable<RandomNumberEntity> getRandomNumber (@Body RequestBody reoud);
}
