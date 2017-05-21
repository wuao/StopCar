package com.sanxiongdi.stopcar.entity;

/**
 * Created by wuaomall@gmail.com on 2017/5/15.
 */

public class UserInfoEntity extends BaseEntity {

    public   String name;//用户昵称
    public  String  phone;//手机号
    public  String  login;// 密码
    public  String  image;//头像
    public  String  ref;//随机号
    public  String  age;//年龄
    public  String  sex;//性别
    public  String car_user_state; //是否在线 0  不在线 1在线 非必需
    public  String street;//地址
    public  String car_user_phone_id;//设备id
    public  String login_date;//最近登录时间
    public  String car_user_online;//是否在线 0  不在线 1在线 非必需
    public  String car_user_vip_out_date;//月卡到期时间 内部员工不需要收费 所以在写逻辑的时候需要注意


}
