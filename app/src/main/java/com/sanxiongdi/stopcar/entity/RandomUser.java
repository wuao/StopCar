package com.sanxiongdi.stopcar.entity;

/**
 * Created by wuaomall@gmail.com on 2017/5/1.
 */

public class RandomUser  extends BaseEntity{
    private  int state;

    private  int result;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RandomUser{" +
                "state=" + state +
                ", result=" + result +
                '}';
    }


    public RandomUser(int state, int result) {
        this.state = state;
        this.result = result;
    }


    public RandomUser() {
      super();
    }
}
