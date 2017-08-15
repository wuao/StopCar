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
 * 获取随机生成的ID
 * Created by lin.woo on 2017/5/5.
 */

public class CreateAccountPresenter extends BasePresenter<IModifyOrder> {
    public CreateAccountPresenter(Context context, IModifyOrder view) {
        super(context, view);
    }

    public void getRandomId(String name, String login) {
        put("method", "res.users.create");
        put2("name", name);
        put2("login", login);
        List<HashMap<String,Object>> list = new ArrayList<>();
        list.add(map2);
        put("args",list);

        ApiExecutor.getInstance().getRandomUser(initGson().toJson(map1))
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
