package com.sanxiongdi.stopcar.presenter.view;

import com.sanxiongdi.stopcar.entity.WrapperEntity;

/**
 * 创建账号
 * Created by lin.woo on 2017/5/5.
 */

public interface IModifyOrder {

    void modifyOrderSuccess(WrapperEntity list);


    void modifyOrderFailure(boolean isRequest, int code, String msg);



}
