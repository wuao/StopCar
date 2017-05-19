package com.sanxiongdi.stopcar.presenter.view;

import com.sanxiongdi.stopcar.entity.RandomUser;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;

import java.util.List;

/**
 * Created by wuaomall@gmail.com on 2017/5/15.
 */

public interface IUserInfoSeting {

    void queryUserInfoSeting(WrapperEntity<List<UserInfoEntity>> list);

    void updataUserInfoSeting(List<RandomUser> list);

    void queryOrderFailure(boolean isRequest, int code, String msg);
    void queryOrderSuccess(List<UserInfoEntity> list);


}
