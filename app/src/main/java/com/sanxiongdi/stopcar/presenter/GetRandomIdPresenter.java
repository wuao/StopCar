package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

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
        ApiExecutor.getInstance().getRandomNumber(star())
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

    public String star() {
        Gson gson = new Gson();
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        HashMap<String, Object> pr2 = new HashMap<String, Object>();
        pr2.put("name", "title");
        paramsMap.put("login", "admin");
        paramsMap.put("password", "admin");
        paramsMap.put("method", "res.users.random_id");
        paramsMap.put("args", new Array[]{});
        String strEntity = gson.toJson(paramsMap);
        return strEntity;
    }
}
