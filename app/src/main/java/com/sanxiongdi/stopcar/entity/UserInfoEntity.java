package com.sanxiongdi.stopcar.entity;

/**
 * Created by wuaomall@gmail.com on 2017/5/15.
 */

public class UserInfoEntity extends BaseEntity {

    public   String name;//用户昵称
    public  String car_user_callphone_number;//手机号
    public  String car_user_state; //是否在线 0  不在线 1在线 非必需
    public  String car_user_address;//地址
    public  String car_user_login_time;//最近登录时间
    public  String car_user_online;//是否在线 0  不在线 1在线 非必需
    public  String car_user_vip_out_date;//月卡到期时间 内部员工不需要收费 所以在写逻辑的时候需要注意


}
