package com.sanxiongdi.stopcar.network.inter;

import com.sanxiongdi.stopcar.entity.RandomUser;
import com.sanxiongdi.stopcar.entity.WrapperEntity;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wuaomall@gmail.com on 2017/4/28.
 */

public interface ApiService {
    //获取随机账号
    @GET("api/rest")
    Observable<WrapperEntity> getRandomNumber(@Query("args") String data);

    //创建账号
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity> getRandomUser(@Field("args") String args);

    //创建账号
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity> getCreateOrder(@Field("args") String args);

    //订单查询
    @GET("api/rest")
    Observable<WrapperEntity> getQueryOrder(@Query("args") String args);

}
