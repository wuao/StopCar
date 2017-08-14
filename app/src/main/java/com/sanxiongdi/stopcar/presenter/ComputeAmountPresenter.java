package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.IComputeAmount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 钱包
 * Created by wuaomall@gmail.com on 2017/6/5.
 */

public class ComputeAmountPresenter extends BasePresenter<IComputeAmount> {



    private Map<String, Object> maps;

    public ComputeAmountPresenter(Context context, IComputeAmount view) {
        super(context,  view);
        maps = new HashMap<>();
    }
    /**
     *  查询多少金额
     * @param   order_id 订单id
     */
    public void computeAmount(int  order_id) {
        put("method", "park.order.compute_amount");
        List<Object> lists = new ArrayList<>();
        ArrayList<List> arrayList=new ArrayList<>();
        lists.add(order_id);
        arrayList.add(lists);
        put("args", arrayList);
        ApiExecutor.getInstance().computeAmount(initGson().toJson(map1))
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
                            view.computeAmountFaile(false, -1, "获取金额失败,请检查网络！");
                        } else
                            view.computeAmount(wrapperEntity);
                    }
                });
    }



}
