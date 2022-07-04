package com.puresoftware.quickmemo.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.puresoftware.quickmemo.R;

public class SampleAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_acivity);

        EditText edttext = findViewById(R.id.edt_text);
        edttext.setCursorVisible(false);
    }
}