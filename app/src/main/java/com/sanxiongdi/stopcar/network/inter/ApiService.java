package com.sanxiongdi.stopcar.network.inter;

import com.sanxiongdi.stopcar.entity.Balance;
import com.sanxiongdi.stopcar.entity.CarInfoEntity;
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

    //根据唯一设备号获取 查询
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity> getRandomUser(@Field("args") String args);

    //创建订单
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity> getCreateOrder(@Field("args") String args);

    //创建账号
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity> getCreateUserInfo(@Field("args") String args);


    //根据用户id 查询用户余额
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity<List<Balance>>> getUserByIdBalance(@Field("args") String args);



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


    //创建车辆信息
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity>  createCarInfo(@Field("args") String args);

      //更新车辆信息
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity>  updataCarInfo(@Field("args") String args);

    //获取车辆信息
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity<List<CarInfoEntity>>>  getCarInfo(@Field("args") String args);



    //钱包充值
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity> createWallet(@Field("args") String args);



    //交易记录
    @FormUrlEncoded
    @POST("api/rest")
    Observable<WrapperEntity> createTransaction(@Field("args") String args);



}
