package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebTestActivity extends AppCompatActivity {

    Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_test);

        btnEnter = findViewById(R.id.btnEnter);

        OkHttpClient client = new OkHttpClient();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request request = new Request.Builder()
                .url("https://78dc-58-239-206-38.ngrok.io/project2/pong?name=홍길동&age=12")
                .build();

                client.newCall(request).enqueue(callback);
            }
        });
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body =response.body().string();

            Log.i("gugu", "결과: " + body);

//            success => 로그인성공 페이지
//                    fail => 로그인실패 페이지
        }
    };
}