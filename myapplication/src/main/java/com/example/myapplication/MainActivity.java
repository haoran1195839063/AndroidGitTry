package com.example.myapplication;

import android.annotation.SuppressLint;
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

    private TextView textview2;
    private Button button2;
    private String lujing;
    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String s = msg.obj.toString();
                    textview2.setText(s);

                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview2 = findViewById(R.id.textview2);
        button2 = findViewById(R.id.button2);
        lujing = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
        button2.setOnClickListener(this);
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(lujing);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setRequestMethod("GET");
                    InputStream inputStream = urlConnection.getInputStream();
                    String inputStr = getInputStr(inputStream);
                    Message message1 = new Message();
                    message1.what = 1;
                    message1.obj = inputStr;
                    handler2.sendMessage(message1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    ;

    private String getInputStr(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String strr = null;
        while ((strr = bufferedReader.readLine()) != null) {
            stringBuffer.append(strr);
        }
        return stringBuffer.toString();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                getData();

                break;
        }
    }
}
