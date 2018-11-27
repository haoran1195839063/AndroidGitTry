package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String mUrl;
    private TextView text1;
    private Button button1;
    private Handler Myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 1:
                    //读取到数据给textview赋值
                    String s = msg.obj.toString();
                    text1.setText(s);
                    break;
                case 2:

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUrl = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
        text1 = findViewById(R.id.textview1);
        button1 = findViewById(R.id.dianji);
        button1.setOnClickListener(this);

    }


    private void getNetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //定位一个网址服务
                    URL url = new URL(mUrl);
                    //由一个有效的网址服务返回这个对象
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //get的方式访问网址
                    urlConnection.setRequestMethod("GET");
                    //五秒后访问失败
                    urlConnection.setConnectTimeout(5000);
                    //输入流
                    InputStream inputStream = urlConnection.getInputStream();
                    String inputStr = getInputStr(inputStream);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = inputStr;
                    //发送到handler
                    Myhandler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    //io流读取
    private String getInputStr(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dianji:
                getNetData();
                break;
        }

    }
}
