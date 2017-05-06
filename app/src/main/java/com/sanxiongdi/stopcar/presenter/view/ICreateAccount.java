package com.sanxiongdi.stopcar.presenter.view;

/**
 * 创建账号
 * Created by lin.woo on 2017/5/5.
 */

public interface ICreateAccount {
    void createAccountSuccess();

    void createAccountFailure(boolean isRequest, int code, String msg);
}
