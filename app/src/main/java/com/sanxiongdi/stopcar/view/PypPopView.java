package com.sanxiongdi.stopcar.view;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.pingplusplus.android.Pingpp;
import com.sanxiongdi.stopcar.R;
import com.sanxiongdi.stopcar.uitls.StringUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wuaomall@gmail.com on 2017/6/1.
 */

public class PypPopView extends PopupWindow implements View.OnClickListener {


    private Context context;

    private View view;
    private LinearLayout Wechat,zhifubao_pay;
    private ImageView img_close;
    private EditText my_edit_wallet;
    private static String YOUR_URL ="http://218.244.151.190/demo/charge";
    public static final String CHARGE_URL = YOUR_URL;
    public  OnBackGetBranch onBackGetBranch=null;

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_QPAY = "qpay";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";

    public PypPopView(Context context) {
        super(context);
        this.context=context;
        initview();
    }





    private void initview() {

        view = View.inflate(context, R.layout.top_up_pop, null);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.transparent));
        zhifubao_pay=(LinearLayout) view.findViewById(R.id.zhifubao_pay);
        img_close=(ImageView)view.findViewById(R.id.img_close);
        my_edit_wallet=(EditText)view.findViewById(R.id.my_edit_wallet);
        zhifubao_pay.setEnabled(false);
        initListener();


    }


    public void initListener() {
        zhifubao_pay.setOnClickListener(this);
        img_close.setOnClickListener(this);
        my_edit_wallet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if (my_edit_wallet.getText().toString()!=null){
                   zhifubao_pay.setEnabled(true);
               }else {
                   zhifubao_pay.setEnabled(false);
               }


            }
        });
    }

    public void show() {
        this.showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }


    @Override
    public void onClick(View v) {
        if (v == zhifubao_pay){
            String amountText = my_edit_wallet.getText().toString();
            int amount = Integer.valueOf(amountText);
            if (StringUtils.checkNull(my_edit_wallet.getText().toString())){
                Toast.makeText(context,"请输入金额",Toast.LENGTH_SHORT).show();
            }else {
                if (onBackGetBranch!=null){
                    onBackGetBranch.getBranch(my_edit_wallet.getText().toString().trim());
                }
                new PaymentTask().execute(new PaymentRequest(CHANNEL_ALIPAY, amount));
                this.dismiss();
            }

        }else if (v ==img_close ){
           this.dismiss();
        }

    }

    class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {

        @Override
        protected void onPreExecute() {
            //按键点击之后的禁用，防止重复点击
        }

        @Override
        protected String doInBackground(PaymentRequest... pr) {

            PaymentRequest paymentRequest = pr[0];
            String data = null;
            try {
                JSONObject object = new JSONObject();
                object.put("channel", paymentRequest.channel);
                object.put("amount", paymentRequest.amount);
                String json = object.toString();
                //向Your Ping++ Server SDK请求数据
                data = postJson(CHARGE_URL, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        /**
         * 获得服务端的charge，调用ping++ sdk。
         */
        @Override
        protected void onPostExecute(String data) {
            if(null == data){


                showMsg("请求出错", "请检查URL", "URL无法获取charge");
                return;
            }
            Log.d("charge", data);

            Pingpp.createPayment((Activity) context, data);
        }

    }


    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null !=msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null !=msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(str);
        builder.setTitle("提示");
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    /**
     * 获取charge
     * @param urlStr charge_url
     * @param json 获取charge的传参
     * @return charge
     * @throws IOException
     */
    private static String postJson(String urlStr, String json) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.getOutputStream().write(json.getBytes());

        if(conn.getResponseCode() == 200) {
            BufferedReader
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
        return null;
    }


    class PaymentRequest {
        String channel;
        int amount;

        public PaymentRequest(String channel, int amount) {
            this.channel = channel;
            this.amount = amount;
        }
    }


    public  interface   OnBackGetBranch{

        void getBranch(String branch);
    }

    public void setOnBackGetBranch(OnBackGetBranch onBackGetBranch) {
        this.onBackGetBranch = onBackGetBranch;
    }
}


