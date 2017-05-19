package com.sanxiongdi.stopcar.presenter.view;

import com.sanxiongdi.stopcar.entity.QueryOrderEntity;

import java.util.List;

/**
 * 查询订单
 * Created by lin.woo on 2017/5/6.
 */

public interface IQueryOrder {
    void queryOrderSuccess(List<QueryOrderEntity> list);

    void queryOrderFailure(boolean isRequest, int code, String msg);
}
