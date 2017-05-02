package com.sanxiongdi.stopcar.network.inter;

import com.sanxiongdi.stopcar.entity.RandomUser;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 *
 * Created by wuaomall@gmail.com on 2017/5/1.
 */

public interface RandomUserService {

    @Headers({"Content-type:application/x-www-form-urlencoded"})
    @POST("api/rest")
    Call<RandomUser> getRandomUser (@Body RequestBody reoud);
}
