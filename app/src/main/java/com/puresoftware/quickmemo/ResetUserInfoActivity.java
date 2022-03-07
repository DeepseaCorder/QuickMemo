package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ResetUserInfoActivity extends AppCompatActivity {

    EditText edtEmail;
    TextView tvMessage;
    TextView btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_user_info);

        edtEmail = findViewById(R.id.edt_reset_user_activity_email);
        tvMessage = findViewById(R.id.tv_reset_user_activity_message);
        btnOk = findViewById(R.id.tv_reset_user_activity_call);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}