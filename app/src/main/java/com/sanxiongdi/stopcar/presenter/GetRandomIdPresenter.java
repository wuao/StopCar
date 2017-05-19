package com.sanxiongdi.stopcar.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.IGetRandomId;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 获取随机生成的ID
 * Created by lin.woo on 2017/5/5.
 */

public class GetRandomIdPresenter extends BasePresenter<IGetRandomId> {
    public GetRandomIdPresenter(Context context, IGetRandomId view) {
        super(context, view);
    }

    public void getRandomId() {
        put("method", "res.users.random_id");
        put("args", map2);
        ;
        ApiExecutor.getInstance().getRandomNumber(initGson().toJson(map1))
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
                        view.getRandomIdSuccess((List<String>) wrapperEntity.result);
                    }
                });
    }

}
