package com.sanxiongdi.stopcar.network.inter;

/**
 * Created by lin.woo on 2017/5/3.
 */
public class ApiExecutor {

    private static class SingletonHolder {
        private static final RandomService INSTANCE = ApiService.Factory.create();
    }

    public static final RandomService getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
