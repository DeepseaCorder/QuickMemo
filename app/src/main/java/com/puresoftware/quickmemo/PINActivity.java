package com.puresoftware.quickmemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PINActivity extends AppCompatActivity {

    int pin[] = new int[3];

    ConstraintLayout pinLayout;
    TextView pin1;
    TextView pin2;
    TextView pin3;
    TextView pin4;
    LinearLayout resetUserInfo;
//    EditText edtKeyboard;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinactivity);

        pinLayout = findViewById(R.id.lay_pin_activity_keyboard_use);
        pin1 = findViewById(R.id.tvimg_pin_activity_1_pin);
        pin2 = findViewById(R.id.tvimg_pin_activity_2_pin);
        pin3 = findViewById(R.id.tvimg_pin_activity_3_pin);
        pin4 = findViewById(R.id.tvimg_pin_activity_4_pin);
        resetUserInfo = findViewById(R.id.laybtn_pin_activity_reset_user_info);
//        edtKeyboard = findViewById(R.id.edt_pin_activity_keyboard);

//        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        imm.showSoftInput(edtKeyboard, 0);
//        edtKeyboard.requestFocus();

//        https://ellordnet.tistory.com/28
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

//        // https://aries574.tistory.com/203 이거 참조해서 온갖 꼼수부림
//        // 1. VISIBLE 상태를 두되, textcolr 바꾸고 아무데나 터치하면 리스너 인식받고 다시 키보드 띄우게 하기.?
//        // 1번 방법은 pin모드가 안되고, 잘 되지도 않음.
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        requestShowKeyboardShortcuts();
    }
}