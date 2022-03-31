package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail;
    EditText edtPW;
    TextView btnOk;
    TextView btnRegister;
    TextView btnResetAccount;
    TextView tvMessage;

    String TAG = LoginActivity.class.getSimpleName();

    // Spring서버, 안드로이드 개발, Java

//    title, content, timestamp, email => 객체 => json => String
//    editor.pusString("")

//    json객체로 변환

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edt_login_activity_email);
        edtPW = findViewById(R.id.edt_login_activity_pw);
        btnOk = findViewById(R.id.tv_reset_password_ok);
        btnRegister = findViewById(R.id.tv_login_activity_register);
        btnResetAccount = findViewById(R.id.tv_login_activity_find_account);
        tvMessage = findViewById(R.id.tv_login_message);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = edtEmail.getText().toString();
                String PW = edtPW.getText().toString();

                if (Email.equals("") || PW.equals("")) {


                } else {

                    OkHttpClient client = new OkHttpClient();

                    Log.i(TAG, "email: " + Email);
                    Log.i(TAG, "pw: " + PW);
                    Request request = new Request.Builder()
                            .url("https://9bc0-58-239-206-38.ngrok.io/quick/login?" + "userEmail=" + Email + "&userPW=" + PW)
                            .build();

                    client.newCall(request).enqueue(callback);


                }


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        btnResetAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ResetUserInfoActivity.class);
                startActivity(intent);
            }
        });

    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.i(TAG, "login failed");

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.i(TAG, body);

            try {
                JSONObject root = new JSONObject(body);
                int userExist = root.getInt("userExist");

                if (userExist == 1) {
                    Intent intent = new Intent(LoginActivity.this, AppSettingsActivity.class);

                    startActivity(intent);
                    finish();

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvMessage.setVisibility(View.INVISIBLE);
                            tvMessage.setVisibility(View.VISIBLE);
                            tvMessage.setText("정보가 일치하지 않습니다");
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

}