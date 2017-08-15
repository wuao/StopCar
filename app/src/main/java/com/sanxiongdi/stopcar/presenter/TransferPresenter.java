package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.ITransferOrder;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 修改订单
 * Created by lin.woo on 2017/5/5.
 */

public class TransferPresenter extends BasePresenter<ITransferOrder> {
    public TransferPresenter(Context context, ITransferOrder view) {
        super(context, view);
    }

    /**
     * 传入order id  和需要转让id 转移订单
     * @param orderid
     */
    public void transferOrder(int orderid,String authorize_id) {
        put("method", "park.order.write");
        List<Object> listWrapper = new ArrayList<>();
        put2("car_order_authorize_id",authorize_id );
        List<Object> listId = new ArrayList<>();
        listId.add(orderid);
        listWrapper.add(listId);
        listWrapper.add(map2);
        put("args", listWrapper);
        ApiExecutor.getInstance().transferOrder(initGson().toJson(map1))
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
                        if (wrapperEntity != null || wrapperEntity.state != 1) {
                            view.transferOrderFailure(false, -1, "");
                        } else {
                            view.transferOrderSuccess(wrapperEntity);
                        }
                    }
                });
    }
}
