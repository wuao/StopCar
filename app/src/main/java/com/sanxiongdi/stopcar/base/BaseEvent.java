package com.sanxiongdi.stopcar.base;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wuaomall@gmail.com on 2017/8/16.
 */

public class BaseEvent extends EventBus {
    private boolean isSuccess;


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
