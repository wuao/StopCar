package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.ICreateAccount;

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

    public void getRandomId(String name, String login) {
        put("method", "park.order.search_read");


        put("args",map2);

//                               [[['user_id','=',14]],['name','car_order_start_date']],
//        'kwargs':{'limit':10,'offset':0}


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

}
