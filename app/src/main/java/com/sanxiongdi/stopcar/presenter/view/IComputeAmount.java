package com.sanxiongdi.stopcar.presenter.view;

import com.sanxiongdi.stopcar.entity.WrapperEntity;

/**
 * Created by wuaomall@gmail.com on 2017/6/5.
 */

public interface IComputeAmount {

    //缴纳金额
    void computeAmount(WrapperEntity list);

    //钱包充值失败接口
    void computeAmountFaile(boolean isRequest, int code, String msg);

}
