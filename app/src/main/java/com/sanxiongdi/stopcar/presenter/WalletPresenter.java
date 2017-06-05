package com.sanxiongdi.stopcar.presenter;

import android.content.Context;

import com.sanxiongdi.stopcar.base.BasePresenter;
import com.sanxiongdi.stopcar.entity.Wallet;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.network.inter.ApiExecutor;
import com.sanxiongdi.stopcar.presenter.view.Iwallet;

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

public class WalletPresenter  extends BasePresenter<Iwallet> {



    private Map<String, Object> maps;

    public WalletPresenter(Context context, Iwallet view) {
        super(context,  view);
        maps = new HashMap<>();
    }
    /**
     *  充值
     * @param  wallet
     */
    public void createWallet(Wallet wallet) {
        put("method", "park.wallet.create");
        List<Object> lists = new ArrayList<>();
        Map<String, Object> listparam = new HashMap<>();
        listparam.put(wallet.user_id, wallet.user_id);
        listparam.put(wallet.order_id, wallet.order_id);
        listparam.put(wallet.amount, wallet.amount);
        listparam.put(wallet.state, wallet.state);
        lists.add(listparam);
        put("args", lists);
        ApiExecutor.getInstance().createWallet(initGson().toJson(map1))
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
                            view.Walletfaile(false, -1, "钱包充值失败,请检查网络！");
                        } else
                            view.createWallet(wrapperEntity);
                    }
                });
    }

    /**
     *  交易记录
     * @param  wallet
     */
    public void createTransaction(Wallet wallet) {

        put("method", "park.wallet.create");
        List<Object> lists = new ArrayList<>();
        Map<String, Object> listparam = new HashMap<>();
        listparam.put(wallet.user_id, wallet.user_id);
        listparam.put(wallet.order_id, wallet.order_id);
        listparam.put(wallet.amount, wallet.amount);
        listparam.put(wallet.state, wallet.state);
        lists.add(listparam);
        put("args", lists);
        ApiExecutor.getInstance().createTransaction(initGson().toJson(map1))
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
                            view.Walletfaile(false, -1, "付款失败,请检查网络！");
                        } else
                            view.createWallet(wrapperEntity);
                    }
                });
    }



}
