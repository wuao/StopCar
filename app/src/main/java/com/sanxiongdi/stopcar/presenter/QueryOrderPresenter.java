package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.ICreateAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 获取随机生成的ID
 * Created by lin.woo on 2017/5/5.
 */

public class QueryOrderPresenter extends BasePresenter<ICreateAccount> {
    public QueryOrderPresenter(Context context, ICreateAccount view) {
        super(context, view);
    }

    public void getRandomId() {

        setData1();
        ApiExecutor.getInstance().getQueryOrder(initGson().toJson(map1))
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

    private void setData1() {
        put("method", "park.order.search_read");

        List<Object> lists = new ArrayList<>();
        List<String> list = new ArrayList<>();
        lists.add(list);
        list = new ArrayList<>();
        list.add("name");
        list.add("car_order_number");
        list.add("car_order_start_date");
        list.add("car_order_user_id");
        list.add("car_order_stop_date");
        list.add("car_order_authorize_id");
        list.add("car_order_state");
        list.add("car_order_stop_state");
        list.add("car_order_user_id");
        list.add("car_order_id");
        put("args", map2);

        Map<String, Object> maps = new HashMap<>();
        maps.put("limit", 10);
        maps.put("offset", 0);
        put("kwargs", maps);
    }

    private void setData2() {
        put("method", "park.order.search_read");
        List<Object> lists = new ArrayList<>();
        List<List<Object>> lists2 = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        list.add("car_order_state");
        list.add("=");
        list.add("1");
        lists2.add(list);
        lists.add(lists2);
        put("args", map2);

        Map<String, Object> maps = new HashMap<>();
        maps.put("limit", 10);
        maps.put("offset", 0);
        put("kwargs", maps);
    }

}
