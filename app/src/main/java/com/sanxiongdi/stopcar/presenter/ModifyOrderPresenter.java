package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.ICreateAccount;

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

public class ModifyOrderPresenter extends BasePresenter<ICreateAccount> {
    public ModifyOrderPresenter(Context context, ICreateAccount view) {
        super(context, view);
    }

    public void createOrder() {
        put("method", "park.order.write");
        List<Object> listWrapper = new ArrayList<>();
        put2("car_order_stop_state", "1");
        put2("car_order_state", "1");
        List<Integer> listId = new ArrayList<>();
        listId.add(26);

        listWrapper.add(listId);
        listWrapper.add(map2);
        put("args", listWrapper);

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
