package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

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

    /**
     *  创建车辆信息
     * @param carInfoEntity
     */
    public void createCarInfo(CarInfoEntity carInfoEntity) {
        put("method", "park.car.create");
        List<Object> lists = new ArrayList<>();
        Map<String, Object> listparam = new HashMap<>();
        listparam.put("name", carInfoEntity.name);
        listparam.put("user_id", carInfoEntity.user_id+"");
        listparam.put("note", carInfoEntity.note);
        listparam.put("color", carInfoEntity.color);
        listparam.put("vol", carInfoEntity.vol);
        listparam.put("car_brand_name", carInfoEntity.car_brand_name);
        lists.add(listparam);
        put("args", lists);
        ApiExecutor.getInstance().createCarInfo(initGson().toJson(map1))
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
                            view.queryCarFailure(false, -1, "保存失败,请检查网络！");
                        } else
                            view.createCarInfoSuccess(wrapperEntity);
                    }
                });

    }

    /**
     *   根据id 更新车辆信息
     * @param carid
     * @param carInfoEntity
     */
    public void updataCarInfo( int  carid, CarInfoEntity carInfoEntity) {
        put("method", "park.car.write");
        List<Object> list2 = new ArrayList<>();
        List<Object> lists = new ArrayList<>();
        Map<String, Object> listparam = new HashMap<>();
        List<Object> list = new ArrayList<>();
        listparam.put("name", carInfoEntity.name);
        listparam.put("user_id", carInfoEntity.user_id);
        listparam.put("note", carInfoEntity.note);
        listparam.put("color", carInfoEntity.color);
        listparam.put("vol", carInfoEntity.vol);
        listparam.put("car_brand_name", carInfoEntity.car_brand_name);
        list2.add(carid);
        lists.add(list2);
        lists.add(listparam);
        put("args", lists);
        ApiExecutor.getInstance().updataCarInfo(initGson().toJson(map1))
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
                            view.queryCarFailure(false, -1, "更新失败,请检查网络！");
                        } else
                            view.createCarInfoSuccess(wrapperEntity);
                    }
                });
    }

    /**
     * 根据用户id 查询车辆信息
     * @param userid
     */
    public void getCarInfo( int  userid) {
        put("method", "park.car.search_read");
        List<Object> lists = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        List<List<Object>> lists2 = new ArrayList<>();
        list.add("user_id");
        list.add("=");
        list.add(userid);
        lists.add(list);
        list = new ArrayList<>();
        list.add("name");
        list.add("note");
        list.add("color");
        list.add("vol");
        list.add("car_brand_name");
        lists2.add(lists);
        lists2.add(list);
        put("args", lists2);
        ApiExecutor.getInstance().getCarInfo(initGson().toJson(map1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WrapperEntity<List<CarInfoEntity>>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(WrapperEntity<List<CarInfoEntity>> wrapperEntity) {
                        if (wrapperEntity == null || wrapperEntity.state != 1) {
                            view.queryCarFailure(false, -1, "获取车辆信息失败,请检查网络");
                        } else
                            view.queryCarSuccess(wrapperEntity.result);
                    }
                });

    }
}
