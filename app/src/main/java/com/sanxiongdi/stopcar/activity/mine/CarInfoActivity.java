package com.sanxiongdi.stopcar.activity.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.presenter.view.ICarInfo;

/**
 * Created by wuaomall@gmail.com on 2017/5/20.
 */

public class CarInfoActivity extends BaseActivity implements View.OnClickListener,ICarInfo {

    private EditText car_name_edit, car_number_edit, car_pailiang_edit, car_color_edit, car_shuoming_edit;
    private TextView car_name_text, car_number_text, car_pailiang_text, car_color_text, car_shuoming_text, text_back_uitls, edit_uitl_save;
    private ImageView img_back;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.car_updata_info);
        findView();
        setListeners();

    }

    @Override
    protected void findView() {
        img_back = (ImageView) findViewById(R.id.user_menage_tool_bar).findViewById(R.id.img_back);
        text_back_uitls = (TextView) findViewById(R.id.user_menage_tool_bar).findViewById(R.id.text_back_uitls);
        edit_uitl_save = (TextView) findViewById(R.id.user_menage_tool_bar).findViewById(R.id.edit_uitl_save);


        car_name_edit = (EditText) findViewById(R.id.car_name).findViewById(R.id.edit_uitls);
        car_number_edit = (EditText) findViewById(R.id.car_number).findViewById(R.id.edit_uitls);
        car_pailiang_edit = (EditText) findViewById(R.id.car_pailiang).findViewById(R.id.edit_uitls);
        car_color_edit = (EditText) findViewById(R.id.car_color).findViewById(R.id.edit_uitls);
        car_shuoming_edit = (EditText) findViewById(R.id.car_shuoming).findViewById(R.id.edit_uitls);

        car_name_text = (TextView) findViewById(R.id.car_name).findViewById(R.id.text_nicheng);
        car_number_text = (TextView) findViewById(R.id.car_number).findViewById(R.id.text_nicheng);
        car_pailiang_text = (TextView) findViewById(R.id.car_pailiang).findViewById(R.id.text_nicheng);
        car_color_text = (TextView) findViewById(R.id.car_color).findViewById(R.id.text_nicheng);
        car_shuoming_text = (TextView) findViewById(R.id.car_shuoming).findViewById(R.id.text_nicheng);


        car_name_text.setText("车  名");
        car_number_text.setText("车  牌");
        car_pailiang_text.setText("排  量");
        car_color_text.setText("颜  色");
        car_shuoming_text.setText("说  明");
        setednbeldfalse();

    }

    @Override
    protected void getInstance() {

    }

    @Override
    protected void setListeners() {
        img_back.setOnClickListener(this);
        text_back_uitls.setOnClickListener(this);
        edit_uitl_save.setOnClickListener(this);


    }

    @Override
    protected void setUpDate(Bundle savedInstanceState) {

    }

    @Override
    protected void protectApp() {
        super.protectApp();
    }

    @Override
    protected void kickOut() {
        super.kickOut();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return super.onMenuItemClick(item);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        } else if (v == edit_uitl_save) {
            if ("编   辑".equals(edit_uitl_save.getText().toString().trim())) {
                setendbed();
                setsave();

            } else if ("完   成".equals(edit_uitl_save.getText().toString().trim())) {
                //提交数据

                setsuccful();

            }
        }

    }


    public void setendbed() {
        car_name_edit.setEnabled(true);
        car_number_edit.setEnabled(true);
        car_pailiang_edit.setEnabled(true);
        car_color_edit.setEnabled(true);
        car_shuoming_edit.setEnabled(true);
    }


    public void setednbeldfalse() {
        car_name_edit.setEnabled(false);
        car_number_edit.setEnabled(false);
        car_pailiang_edit.setEnabled(false);
        car_color_edit.setEnabled(false);
        car_shuoming_edit.setEnabled(false);
    }

    /**
     * 设置空间参数
     * 获取值
     */
    public void setsuccful() {
        edit_uitl_save.setText("编   辑");
        setednbeldfalse();
        img_back.setVisibility(View.VISIBLE);
        text_back_uitls.setVisibility(View.INVISIBLE);

    }
    public void setsave() {
        edit_uitl_save.setText("完   成");
        edit_uitl_save.setTextColor(Color.rgb(30,144,255));
        setendbed();
    }

    @Override
    public void createCarInfoSuccess(WrapperEntity list) {
        if (list.result!=null||list.result.equals("")) {
            Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void queryCarFailure(boolean isRequest, int code, String msg) {

        if (code == -1) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

    }
}


