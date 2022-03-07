package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText edtResetPW;
    EditText edtCheckResetPW;
    TextView tvMessage;
    TextView btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtResetPW = findViewById(R.id.edt__reset_password_pw);
        edtCheckResetPW = findViewById(R.id.edt__reset_password_checkpw);
        tvMessage = findViewById(R.id.tv_reset_password_message);
        btnOk = findViewById(R.id.tv_reset_password_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}