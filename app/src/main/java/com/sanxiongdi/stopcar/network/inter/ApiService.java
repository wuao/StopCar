package com.sanxiongdi.stopcar.network.inter;

import com.sanxiongdi.stopcar.entity.QueryOrderEntity;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Observable<WrapperEntity<List<QueryOrderEntity>>> getQueryOrder(@Query("args") String args);

    //更新用户信息
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity>  updataUser(@Field("args") String args);

    //获取用户信息
    @GET("api/rest")
    Observable<WrapperEntity<List<UserInfoEntity>>>  queryUser(@Query("args") String args);


    //提交反馈信息
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity>  createHelpInfo(@Field("args") String args);



    //提交反馈信息
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity>  updataCarInfo(@Field("args") String args);


}