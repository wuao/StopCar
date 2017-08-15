package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.IModifyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建订单
 * Created by lin.woo on 2017/5/5.
 */

public class CreateOrderPresenter extends BasePresenter<IModifyOrder> {
    public CreateOrderPresenter(Context context, IModifyOrder view) {
        super(context, view);
    }

    public void createOrder(String userId) {
        put("method", "park.order.create");
        List<HashMap<String, Object>> list = new ArrayList<>();
        put2("car_order_stop_state", "2");
        put2("car_order_state", "1");
        put2("car_order_user_id", userId);
        list.add(map2);
        put("args", list);
        ApiExecutor.getInstance().getCreateOrder(initGson().toJson(map1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WrapperEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(WrapperEntity wrapperEntity) {
                    }
                });
    }

}
