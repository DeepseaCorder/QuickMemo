package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    CheckBox cbPolicy;
    CheckBox cbPrivacy;
    CheckBox cbAllCheck;
    EditText edtEmail;
    EditText edtPW;
    EditText edtCheckPW;
    EditText edtBirth;
    TextView tvMessage;
    TextView btnOk;

    String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cbPolicy = findViewById(R.id.cb_register_activity_policy);
        cbPrivacy = findViewById(R.id.cb_register_activity_personal_privacy);
        cbAllCheck = findViewById(R.id.cb_register_activity_allcheck);
        edtEmail = findViewById(R.id.edt_register_activity_email);
        edtPW = findViewById(R.id.edt_register_activity_pw);
        edtCheckPW = findViewById(R.id.edt_register_activity_checkpw);
        edtBirth = findViewById(R.id.edt_register_activity_birth);
        tvMessage = findViewById(R.id.tv_register_activity_message);
        btnOk = findViewById(R.id.tv_register_activity_ok);

        // 전화번호(정규표현식)이메일(연동) 비밀번호(해시코드)

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = edtEmail.getText().toString().trim();
                String PW = edtPW.getText().toString().trim();
                String checkPW = edtCheckPW.getText().toString().trim();
                String birth = edtBirth.getText().toString();

                //
                if (cbPolicy.isChecked() == true && cbPrivacy.isChecked() == true) {

                    if (Email.equals("") || PW.equals("") || checkPW.equals("") || birth.equals("")) {
                        Log.i(TAG, "not data");
                    } else {

                        OkHttpClient client = new OkHttpClient();

//                         post파라미터 구성
                        RequestBody body = new FormBody.Builder()
                                .add("userEmail", Email)
                                .add("userPW", PW)
                                .add("userBirth", birth)
                                .build();
//
                        Request request = new Request.Builder()
                                .url("https://9bc0-58-239-206-38.ngrok.io/quick/register")
                                .post(body)
                                .build();

                        client.newCall(request).enqueue(callback);
                    }

                } else {
                    Log.i(TAG, "not check");
                }
            }
        });
    }
    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.i(TAG,"register data failed");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.i(TAG,"register data succeced");

            // json
            String body = response.body().string();
            Log.i(TAG, body);
        }
    };
}