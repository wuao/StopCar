package com.sanxiongdi.stopcar.presenter.view;

import com.sanxiongdi.stopcar.entity.WrapperEntity;

/**
 * 创建账号
 * Created by lin.woo on 2017/5/5.
 */

public interface ITransferOrder {

    void transferOrderSuccess(WrapperEntity list);


    void transferOrderFailure(boolean isRequest, int code, String msg);



}
