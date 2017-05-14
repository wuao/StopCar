package com.sanxiongdi.stopcar.presenter;

import android.content.Context;
import android.util.Log;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.QueryOrderEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.ICreateAccount;
import com.sanxiongdi.stopcar.presenter.view.IQueryOrder;

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

public class QueryOrderPresenter extends BasePresenter<IQueryOrder> {
    public static final String ORDER_STATE_FINISH = "0";//完成
    public static final String ORDER_STATE_PROCEED = "1";//未完成
    public static final String ORDER_STATE_CANCEL = "2";//取消
    public static final String ORDER_STATE_AUTH = "3";//转移
    private Map<String, Object> maps;
    private int limit = 10;
    private int offset = 0;

    public QueryOrderPresenter(Context context, IQueryOrder view) {
        super(context, view);
        maps = new HashMap<>();
    }

    private void getRandomId(String state) {

        put("method", "park.order.search_read");
        List<Object> lists = new ArrayList<>();
        List<List<Object>> lists2 = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        list.add("car_order_state");
        list.add("=");
        list.add(state);
        lists2.add(list);
        lists.add(lists2);
        put("args", map2);
        put("kwargs", maps);

        ApiExecutor.getInstance().getQueryOrder(initGson().toJson(map1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WrapperEntity<List<QueryOrderEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WrapperEntity<List<QueryOrderEntity>> wrapperEntity) {
                        if (wrapperEntity == null || wrapperEntity.state != 1) {
                            view.queryOrderFailure(false, -1, "");
                        } else
                            view.queryOrderSuccess(wrapperEntity.result);
                    }
                });
    }

    public int getOffset() {
        return offset;
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


    public void queryFinishOrder() {
        maps.put("limit", limit);
        maps.put("offset", offset = 0);
        getRandomId(ORDER_STATE_FINISH);
    }

    public void queryFinishOrderMore() {
        maps.put("limit", limit);
        maps.put("offset", offset += 10);
        getRandomId(ORDER_STATE_FINISH);
    }

    public void queryProceedOrder() {
        maps.put("limit", limit);
        maps.put("offset", offset = 0);
        getRandomId(ORDER_STATE_PROCEED);
    }

    public void queryProceedOrderMore() {
        maps.put("limit", limit);
        maps.put("offset", offset += 10);
        getRandomId(ORDER_STATE_PROCEED);
    }

    public void queryCancelOrder() {
        maps.put("limit", limit);
        maps.put("offset", offset = 0);
        getRandomId(ORDER_STATE_CANCEL);
    }

    public void queryCancelOrderMore() {
        maps.put("limit", limit);
        maps.put("offset", offset += 10);
        getRandomId(ORDER_STATE_CANCEL);
    }

    public void queryAuthOrder() {
        maps.put("limit", limit);
        maps.put("offset", offset = 0);
        getRandomId(ORDER_STATE_AUTH);
    }

    public void queryAuthOrderMore() {
        maps.put("limit", limit);
        maps.put("offset", offset += 10);
        getRandomId(ORDER_STATE_AUTH);
    }

}
