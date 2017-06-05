package com.sanxiongdi.stopcar.presenter.view;

import com.sanxiongdi.stopcar.entity.WrapperEntity;

/**
 * Created by wuaomall@gmail.com on 2017/6/5.
 */

public interface Iwallet {

    //钱包充值接口
    void createWallet(WrapperEntity list);

    //钱包充值失败接口
    void Walletfaile(boolean isRequest, int code, String msg);

    //交易接口
    void createTransaction(WrapperEntity list);


}
