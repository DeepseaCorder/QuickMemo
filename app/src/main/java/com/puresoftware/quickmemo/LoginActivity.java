package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail;
    EditText edtPW;
    TextView btnOk;
    TextView btnRegister;
    TextView btnResetAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edt_login_activity_email);
        edtPW = findViewById(R.id.edt_login_activity_pw);
        btnOk = findViewById(R.id.tv_reset_password_ok);
        btnRegister = findViewById(R.id.tv_login_activity_register);
        btnResetAccount = findViewById(R.id.tv_login_activity_find_account);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

}