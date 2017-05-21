package com.sanxiongdi.stopcar.presenter.view;

import com.sanxiongdi.stopcar.entity.WrapperEntity;

/**
 * Created by wuaomall@gmail.com on 2017/5/21.
 */

public interface ICarInfo {
    void createCarInfoSuccess(WrapperEntity list);


    void queryCarFailure(boolean isRequest, int code, String msg);

}
