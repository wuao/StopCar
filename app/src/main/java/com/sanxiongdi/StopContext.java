package com.sanxiongdi;

import com.google.gson.Gson;
import com.sanxiongdi.stopcar.entity.Balance;
import com.sanxiongdi.stopcar.entity.UserInfoEntity;
import com.sanxiongdi.stopcar.uitls.SharedPreferenceUtils;
import com.sanxiongdi.stopcar.uitls.StringUtils;

/**
 * Created by wuaomall@gmail.com on 2017/6/25.
 */

public class StopContext {
    private static StopContext instance;

    public static StopContext getInstance() {
        if (instance == null) {
            instance = new StopContext();
        }
        return instance;
    }


    public UserInfoEntity getUserInfo() {
        String gson = SharedPreferenceUtils
                .getStringValueFromSP("UserInfo", "UserInfo");
        if (!StringUtils.checkNull(gson)) {
            return new Gson().fromJson(gson, UserInfoEntity.class);
        } else {
            return new UserInfoEntity();
        }
    }


    public Balance getBalance() {
        String gson = SharedPreferenceUtils
                .getStringValueFromSP("Balance", "Balance");
        if (!StringUtils.checkNull(gson)) {
            return new Gson().fromJson(gson, Balance.class);
        } else {
            return new Balance();
        }
    }


    public void setUserInfo(String gson) {
        SharedPreferenceUtils.setStringDataIntoSP("UserInfo", "UserInfo", gson);
    }

    public void setbalance(String gson) {
        SharedPreferenceUtils.setStringDataIntoSP("Balance", "Balance", gson);
    }



}
