package com.sanxiongdi.stopcar.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseFrament;
import com.sanxiongdi.stopcar.uitls.RootLayout;

/**
 * Created by wuaomall@gmail.com on 2017/4/7.
 */

public class SearchFragement  extends BaseFrament{

    private Context mContext;
    private  View view;
    private Toolbar toolbar;
    private RootLayout mrootLayout;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragement, container, false);
        toolbar=(Toolbar)view.findViewById(R.id.tool_bar);
        return  view;
    }

    @Override
    protected void initDate(Bundle  mbundle) {

    }

    @Override
    public void  onClick(View view){


    }

    @Override
    protected void initView() {




    }
    @Override
    protected void onsetListener() {

    }
    @Override
    protected int setLayoutResouceId(){

        return R.layout.search_fragement;

    }



}
