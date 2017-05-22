package com.sanxiongdi.stopcar.entity;

/**
 * Created by lin.woo on 2017/5/12.
 */

public class QueryOrderEntity extends BaseEntity {
    public String name;//订单号
    public String car_order_stop_date;
    public String car_order_start_date;//"2017-05-08 13:08:47",//进场时间
    public String car_order_authorize_id;
    public String car_order_state;//订单状态 0 完成，1 未完成，2 取消，3转移
    public String car_order_stop_state;//停放状态(0停放 1离开 2进场)
    public String car_order_user_id;//"1234",用户id
    public int id;//订单id 指的数据库id
    public String display_name;

}
