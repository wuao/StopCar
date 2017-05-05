package com.sanxiongdi.stopcar.network.inter;

import com.sanxiongdi.stopcar.entity.RandomUser;
import com.sanxiongdi.stopcar.entity.WrapperEntity;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wuaomall@gmail.com on 2017/4/28.
 */

public interface ApiService {
    @GET("api/rest")
    Observable<WrapperEntity> getRandomNumber (@Query("args") String reoud);

    @POST("api/rest")
    Call<RandomUser> getRandomUser (@Body RequestBody reoud);
}
