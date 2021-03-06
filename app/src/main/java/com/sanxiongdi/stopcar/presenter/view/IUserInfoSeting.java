package com.sanxiongdi.stopcar.presenter.view;

import com.sanxiongdi.stopcar.entity.Balance;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;

import java.util.List;

/**
 * Created by wuaomall@gmail.com on 2017/5/15.
 */

public interface IUserInfoSeting {


    void queryUserInfoFailure(boolean isRequest, int code, String msg);
    void queryUserInfoSuccess(List<UserInfoEntity> list);
    void queryByPhoneUserInfoSuccess(List<UserInfoEntity> list);
    void queryByPhoneUserInfoFailure(boolean isRequest, int code, String msg);
    void updataUserInfoSuccess(WrapperEntity list);
    void creatUserInfoSuccess(WrapperEntity list);
    void getUserByIdBalance(List<Balance> list);


}
