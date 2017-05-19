package com.sanxiongdi.stopcar.presenter.view;

/**
 * 创建订单
 * Created by lin.woo on 2017/5/5.
 */

public interface ICreateOrder {
    void createOrderSuccess();

    void createOrderFailure(boolean isRequest, int code, String msg);
}
