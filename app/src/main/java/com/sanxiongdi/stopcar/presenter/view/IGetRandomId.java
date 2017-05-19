package com.sanxiongdi.stopcar.presenter.view;

import java.util.List;

/**
 * 获取随机生成ID
 * Created by lin.woo on 2017/5/5.
 */

public interface IGetRandomId {
    void getRandomIdSuccess(List<String> list);
    void getRandomIdFailure(boolean isRequest,int code,String msg);
}
