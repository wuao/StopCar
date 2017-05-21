package com.sanxiongdi.stopcar.activity.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;
import com.sanxiongdi.stopcar.entity.HelpInfoEntity;
import com.sanxiongdi.stopcar.entity.WrapperEntity;
import com.sanxiongdi.stopcar.presenter.HelpInfoPresenter;
import com.sanxiongdi.stopcar.presenter.view.IHlepInfo;

/**
 * 问题反馈
 * Created by wuaomall@gmail.com on 2017/5/20.
 */

public class HelpInfoActivity extends BaseActivity implements View.OnClickListener ,IHlepInfo {

    private EditText help_title_edit, help_ramke_edit;
    private TextView help_title_text, help_ramke_text,edit_uitl_save;
    private ImageView img_back;
    private HelpInfoPresenter helpInfoPresenter;
    private  HelpInfoEntity helpInfoEntity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.help_info);
        helpInfoPresenter=new HelpInfoPresenter(getApplicationContext(), this);
        findView();
        setListeners();

    }

    @Override
    protected void findView() {
        img_back = (ImageView) findViewById(R.id.help_tool_bar).findViewById(R.id.img_back);
        edit_uitl_save=(TextView) findViewById(R.id.help_tool_bar).findViewById(R.id.edit_uitl_save);

        help_title_edit = (EditText) findViewById(R.id.help_title).findViewById(R.id.edit_uitls);
        help_ramke_edit = (EditText) findViewById(R.id.help_ramke).findViewById(R.id.edit_uitls);

        help_title_text = (TextView) findViewById(R.id.help_title).findViewById(R.id.text_nicheng);
        help_ramke_text = (TextView) findViewById(R.id.help_ramke).findViewById(R.id.text_nicheng);

        help_title_text.setText("标  题");
        help_ramke_text.setText("说  明");
        edit_uitl_save.setText("提  交");
        help_ramke_edit.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        help_ramke_edit.setSingleLine(false);
        help_title_edit.setText("");
        help_title_edit.setText("");
        help_ramke_edit.setMinLines(10);
        help_ramke_edit.setMinimumHeight(500);
        help_ramke_edit.setHorizontallyScrolling(false);
        help_title_edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        edit_uitl_save.setTextColor(Color.rgb(30,144,255));
    }

    @Override
    protected void getInstance() {

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
        }if (v==edit_uitl_save){
            //提交数据
            if (senddata()!=null){
                helpInfoPresenter.createHelpInfo(senddata());
                finish();
            }
        }
    }
    @Override
    public void createHelpInfoSuccess(WrapperEntity list) {
        if (list.result!=null||list.result.equals("")) {
            Toast.makeText(getApplicationContext(), "反馈成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "反馈失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void queryHelpFailure(boolean isRequest, int code, String msg) {
      if (code == -1) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }


    public HelpInfoEntity senddata(){
        helpInfoEntity=new HelpInfoEntity();
        if (help_title_edit.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "标题不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }else {
            helpInfoEntity.name=help_title_edit.getText().toString().trim();
            helpInfoEntity.userid="6";
        }
        if (help_ramke_edit.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "内容不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }else {
            helpInfoEntity.car_user_help_content=help_ramke_edit.getText().toString().trim();
        }
        return  helpInfoEntity;
    }


    @Override
    protected void setListeners() {
        img_back.setOnClickListener(this);
        edit_uitl_save.setOnClickListener(this);

    }
}
