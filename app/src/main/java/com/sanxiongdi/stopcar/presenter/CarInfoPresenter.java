package com.sanxiongdi.stopcar.presenter;

import android.content.Context;
import android.util.Log;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.CarInfoEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.ICarInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wuaomall@gmail.com on 2017/5/21.
 */


public class CarInfoPresenter extends BasePresenter<ICarInfo> {

    private Map<String, Object> maps;

    public CarInfoPresenter(Context context, ICarInfo view) {
        super(context, view);
        maps = new HashMap<>();
    }
    public void createHelpInfo(CarInfoEntity carInfoEntity) {
        put("method", "park.help.create");
        List<Object> lists = new ArrayList<>();
        Map<String, Object> listparam = new HashMap<>();
        listparam.put("name", carInfoEntity.name);
        listparam.put("user_id", carInfoEntity.user_id);
        listparam.put("note", carInfoEntity.note);
        lists.add(listparam);
        put("args", lists);
        Log.i("++", map1.values().toString());
        ApiExecutor.getInstance().createHelpInfo(initGson().toJson(map1))
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
                            view.queryCarFailure(false, -1, "更新保存失败,请检查网络！");
                        } else
                            view.createCarInfoSuccess(wrapperEntity);
                    }
                });

    }




}
