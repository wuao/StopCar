package com.sanxiongdi.stopcar.activity.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.base.BaseActivity;

/**
 * 用户修改界面
 * Created by wuaomall@gmail.com on 2017/4/22.
 */

public class UserMineSeting extends BaseActivity  implements View.OnClickListener{

     private EditText  nicheng_edit,age_edit,sex_edit,suijihao_number_edit,number_edit,addess_edit;
     private ImageView img_back;
     private TextView text_back_uitls,edit_uitl_save, nicheng_text,age_text,sex_text,suijihao_number_text,number_text,addess_text;
    private View view;
     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       this.setContentView(R.layout.usermenage_updata_info);
         findView();
        setListeners();
    }

    @Override
    protected void findView() {
        img_back=(ImageView)findViewById(R.id.user_menage_tool_bar).findViewById(R.id.img_back);
        text_back_uitls=(TextView)findViewById(R.id.user_menage_tool_bar).findViewById(R.id.text_back_uitls);
        edit_uitl_save=(TextView)findViewById(R.id.user_menage_tool_bar).findViewById(R.id.edit_uitl_save);

        nicheng_text=(TextView) findViewById(R.id.nicheng).findViewById(R.id.text_nicheng);
        age_text=(TextView) findViewById(R.id.age).findViewById(R.id.text_nicheng);
        sex_text=(TextView) findViewById(R.id.sex).findViewById(R.id.text_nicheng);
        suijihao_number_text=(TextView) findViewById(R.id.suijihao_number).findViewById(R.id.text_nicheng);
        number_text=(TextView) findViewById(R.id.number).findViewById(R.id.text_nicheng);
        addess_text=(TextView) findViewById(R.id.addess).findViewById(R.id.text_nicheng);
        nicheng_text.setText("昵  称");
        age_text.setText    ("年  龄");
        sex_text.setText    ("性  别");
        suijihao_number_text.setText("随机号");
        number_text.setText ("电  话");
        addess_text.setText ("地  址");
        edit_uitl_save.setTextColor(Color.rgb(82,181,239));
        nicheng_edit=(EditText)findViewById(R.id.nicheng).findViewById(R.id.edit_uitls);
        age_edit=(EditText)findViewById(R.id.age).findViewById(R.id.edit_uitls);
        sex_edit=(EditText)findViewById(R.id.sex).findViewById(R.id.edit_uitls);
        suijihao_number_edit=(EditText)findViewById(R.id.suijihao_number).findViewById(R.id.edit_uitls);
        number_edit=(EditText)findViewById(R.id.number).findViewById(R.id.edit_uitls);
        addess_edit=(EditText)findViewById(R.id.addess).findViewById(R.id.edit_uitls);

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
    public void onClick(View v) {

        if (v==img_back){
            finish();
        }if (v == edit_uitl_save){
            if ("编   辑".equals(edit_uitl_save.getText().toString().trim())){
                setsave();
            }else if("完   成".equals(edit_uitl_save.getText().toString().trim())){

                setsuccful();
                //提交数据
            }

        }


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




       public  void  setsave(){
           edit_uitl_save.setText("完   成");
           edit_uitl_save.setTextColor(Color.GRAY);
           setendbed();
       }

    /**
     * 设置空间参数
     * 获取值
     */
    public  void  setsuccful(){
        edit_uitl_save.setText("编   辑");
        setednbeldfalse();
        img_back.setVisibility(View.VISIBLE);
        text_back_uitls.setVisibility(View.INVISIBLE);

    }



     public  void  setendbed(){
         nicheng_edit.setEnabled(true);
         age_edit.setEnabled(true);
         sex_edit.setEnabled(true);
         suijihao_number_edit.setEnabled(true);
         number_edit.setEnabled(true);
         addess_edit.setEnabled(true);
     }

    public  void  setednbeldfalse(){
        nicheng_edit.setEnabled(false);
        age_edit.setEnabled(false);
        sex_edit.setEnabled(false);
        suijihao_number_edit.setEnabled(false);
        number_edit.setEnabled(false);
        addess_edit.setEnabled(false);

    }



}
