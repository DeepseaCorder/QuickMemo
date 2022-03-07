package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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

                        // 순차적 데이터 값 교환.

                    }

                } else {
                    Log.i(TAG, "not check");
                }
            }
        });
    }
}