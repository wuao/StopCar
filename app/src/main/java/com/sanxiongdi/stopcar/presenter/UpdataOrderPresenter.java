package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.IUpdataOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 修改订单
 * Created by lin.woo on 2017/5/5.
 */

public class UpdataOrderPresenter extends BasePresenter<IUpdataOrder> {
    public UpdataOrderPresenter(Context context, IUpdataOrder view) {
        super(context, view);
    }

    /**
     * 传入order id  支付成功修改订单状态
     * @param orderid
     */
    public void updataOrder(int orderid) {
        put("method", "park.order.write");
        List<Object> listWrapper = new ArrayList<>();
        put2("car_order_stop_state", "1");
        put2("car_order_state", "0");
        put2("car_order_stop_date", getData());
        List<Object> listId = new ArrayList<>();
        listId.add(orderid);
        listWrapper.add(listId);
        listWrapper.add(map2);
        put("args", listWrapper);
        ApiExecutor.getInstance().updataOrder(initGson().toJson(map1))
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
                        if (wrapperEntity == null || wrapperEntity.state != 1) {
                            view.updataOrderFailure(false, -1, "");
                        } else {
                            view.updataOrderSuccess(wrapperEntity);
                        }
                    }
                });
    }


    public String getData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }




}
