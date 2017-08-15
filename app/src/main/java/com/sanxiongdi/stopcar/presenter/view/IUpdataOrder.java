package com.sanxiongdi.stopcar.presenter.view;

import com.sanxiongdi.stopcar.entity.WrapperEntity;

/**
 * 创建账号
 * Created by lin.woo on 2017/5/5.
 */

public interface IUpdataOrder {

    void  updataOrderSuccess(WrapperEntity list);


    void  updataOrderFailure(boolean isRequest, int code, String msg);



}
