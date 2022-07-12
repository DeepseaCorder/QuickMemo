package com.puresoftware.quickmemo;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PINsetActivity extends AppCompatActivity {

    ConstraintLayout pinLayout;
    TextView pin1;
    TextView pin2;
    TextView pin3;
    TextView pin4;
    TextView tvMessage;
    TextView tvTitle;

    EditText edtKeyboard;
    String pin = "";
    String beforePin = "";

    String data = "";

    String TAG = PINsetActivity.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinsetactivity);

        // PIN 모드와 PIN 데이터
        SharedPreferences sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        data = sharedPreferences.getString("PIN","");

        pinLayout = findViewById(R.id.tvimg_pin_set_activity_keyboard_use);
        pin1 = findViewById(R.id.tvimg_pin_set_activity_1_pin);
        pin2 = findViewById(R.id.tvimg_pin_set_activity_2_pin);
        pin3 = findViewById(R.id.tvimg_pin_set_activity_3_pin);
        pin4 = findViewById(R.id.tvimg_pin_set_activity_4_pin);
        tvMessage = findViewById(R.id.tvimg_pin_set_activity_message);
        tvTitle = findViewById(R.id.tvimg_pin_set_activity_title);

//        https://ellordnet.tistory.com/28
        edtKeyboard = findViewById(R.id.edt_pin_set_activity_input_number);
        edtKeyboard.requestFocus(); // 이걸 요청하면 원하는 EditText의 키보드가 나온다.
        edtKeyboard.setCursorVisible(false); // 커서 안보이게
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); // 키보드 매니저
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY); // 키보드 자동

        pinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY); // 키보드 자동
            }
        });

        // 0은 초기설정 모드
        if (data.equals("no")) {
            tvTitle.setText("원하는 PIN을 입력해주세요");
            edtKeyboard.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    pin = String.valueOf(charSequence);
                    Log.i(TAG, "i:" + i + ",i1:" + i1 + ",i2:" + i2 + "text:" + charSequence);
                    // i는 길이, i1는 지우면 1, i2는 지우면 0

                    // 텍스트가 있는 경우
                    if (i2 == 1) {
                        switch (i) { // 레드

                            case 0:
                                pin1.setTextColor(Color.parseColor("#FF5353"));
                                break;
                            case 1:
                                pin2.setTextColor(Color.parseColor("#FF5353"));
                                break;
                            case 2:
                                pin3.setTextColor(Color.parseColor("#FF5353"));
                                break;
                            case 3:
                                pin4.setTextColor(Color.parseColor("#FF5353"));
                                break;
                        }
                    } else {
                        switch (i) { // 그레이

                            case 0:
                                pin1.setTextColor(Color.parseColor("#CDCDCD"));
                                break;
                            case 1:
                                pin2.setTextColor(Color.parseColor("#CDCDCD"));
                                break;
                            case 2:
                                pin3.setTextColor(Color.parseColor("#CDCDCD"));
                                break;
                            case 3:
                                pin4.setTextColor(Color.parseColor("#CDCDCD"));
                                break;
                        }
                    }
                    if (i == 3) {

                        // 초기값으로 받고 초기화
                        pin1.setTextColor(Color.parseColor("#CDCDCD"));
                        pin2.setTextColor(Color.parseColor("#CDCDCD"));
                        pin3.setTextColor(Color.parseColor("#CDCDCD"));
                        pin4.setTextColor(Color.parseColor("#CDCDCD"));

                        if (beforePin.equals("")) {
                            beforePin = pin;
                            pin = "";
                            edtKeyboard.setText("");
                            edtKeyboard.getText().toString();
                            tvTitle.setText("확인을 위해 다시 입력해주세요");

                        } else {
                            if (beforePin.equals(pin)) {
                                tvMessage.setVisibility(View.INVISIBLE);
                                editor.putString("PIN",beforePin);
                                editor.commit();
                                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // 키보드 자동 내림
                                Toast.makeText(PINsetActivity.this, "모두 일치합니다", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                tvMessage.setVisibility(View.VISIBLE);
                                pin = "";
                                edtKeyboard.setText("");
                                edtKeyboard.getText().toString();
                            }
                        }
                    }
                }
                @Override
                public void afterTextChanged(Editable editable) {
                }
            });


            // 1은 변경모드
        } else {
            tvTitle.setText("사용중인 PIN을 입력해주세요");
            edtKeyboard.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    pin = String.valueOf(charSequence);
                    Log.i(TAG, "i:" + i + ",i1:" + i1 + ",i2:" + i2 + "text:" + charSequence);
                    // i는 길이, i1는 지우면 1, i2는 지우면 0

                    // 텍스트가 있는 경우
                    if (i2 == 1) {
                        switch (i) { // 레드

                            case 0:
                                pin1.setTextColor(Color.parseColor("#FF5353"));
                                break;
                            case 1:
                                pin2.setTextColor(Color.parseColor("#FF5353"));
                                break;
                            case 2:
                                pin3.setTextColor(Color.parseColor("#FF5353"));
                                break;
                            case 3:
                                pin4.setTextColor(Color.parseColor("#FF5353"));
                                break;
                        }
                    } else {
                        switch (i) { // 그레이

                            case 0:
                                pin1.setTextColor(Color.parseColor("#CDCDCD"));
                                break;
                            case 1:
                                pin2.setTextColor(Color.parseColor("#CDCDCD"));
                                break;
                            case 2:
                                pin3.setTextColor(Color.parseColor("#CDCDCD"));
                                break;
                            case 3:
                                pin4.setTextColor(Color.parseColor("#CDCDCD"));
                                break;
                        }
                    }
                    if (i == 3) {

                        // data가 null이 아닌가?
                        // pin과 data가 일치한가?
                        // -data를 null로 후 초기화한다-

                        //pin과 data가 일치하지 않은가?
                        // -pin 오류 메세지를 출력 후 초기화 한다..-

                        //data가 null인가?
                        // 비밀번호 바꾸는 메소드를 넣는다.

                        // 초기값으로 받고 초기화
                        pin1.setTextColor(Color.parseColor("#CDCDCD"));
                        pin2.setTextColor(Color.parseColor("#CDCDCD"));
                        pin3.setTextColor(Color.parseColor("#CDCDCD"));
                        pin4.setTextColor(Color.parseColor("#CDCDCD"));

                        if (!data.equals("")) {
                            if (pin.equals(data)) {
                                data = "";
                                pin = "";
                                edtKeyboard.setText("");
                                edtKeyboard.getText().toString();
                                tvTitle.setText("원하는 PIN을 입력해주세요");
                                tvMessage.setVisibility(View.INVISIBLE);

                            } else {
                                pin = "";
                                edtKeyboard.setText("");
                                edtKeyboard.getText().toString();
                                tvMessage.setVisibility(View.VISIBLE);
                            }
                        } else {

                            if (beforePin.equals("")) {
                                beforePin = pin;
                                pin = "";
                                edtKeyboard.setText("");
                                edtKeyboard.getText().toString();
                                tvTitle.setText("확인을 위해 다시 입력해주세요");

                            } else {
                                if (beforePin.equals(pin)) {
                                    tvMessage.setVisibility(View.INVISIBLE);
                                    editor.putString("PIN",beforePin);
                                    editor.commit();
                                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // 키보드 자동 내림
                                    Toast.makeText(PINsetActivity.this, "변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();

                                    Log.i(TAG,"beforePIN:"+beforePin+",pin:"+pin);
                                } else {
                                    tvMessage.setVisibility(View.VISIBLE);
                                    pin = "";
                                    edtKeyboard.setText("");
                                    edtKeyboard.getText().toString();
                                }
                            }
                        }
                    }
                }
                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }





        // back up code
//        edtKeyboard.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                pin = String.valueOf(charSequence);
//                Log.i(TAG, "i:" + i + ",i1:" + i1 + ",i2:" + i2 + "text:" + charSequence);
//                // i는 길이, i1는 지우면 1, i2는 지우면 0
//
//                // 텍스트가 있는 경우
//                if (i2 == 1) {
//                    switch (i) { // 레드
//
//                        case 0:
//                            pin1.setTextColor(Color.parseColor("#FF5353"));
//                            break;
//                        case 1:
//                            pin2.setTextColor(Color.parseColor("#FF5353"));
//                            break;
//                        case 2:
//                            pin3.setTextColor(Color.parseColor("#FF5353"));
//                            break;
//                        case 3:
//                            pin4.setTextColor(Color.parseColor("#FF5353"));
//                            break;
//                    }
//                } else {
//                    switch (i) { // 그레이
//
//                        case 0:
//                            pin1.setTextColor(Color.parseColor("#CDCDCD"));
//                            break;
//                        case 1:
//                            pin2.setTextColor(Color.parseColor("#CDCDCD"));
//                            break;
//                        case 2:
//                            pin3.setTextColor(Color.parseColor("#CDCDCD"));
//                            break;
//                        case 3:
//                            pin4.setTextColor(Color.parseColor("#CDCDCD"));
//                            break;
//                    }
//                }
//                if (i == 3) {
//
//                    if (pin.equals("0000")) {
//                        tvMessage.setVisibility(View.INVISIBLE);
//                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // 키보드 자동 내림
//
//                    } else {
//                        tvMessage.setVisibility(View.VISIBLE);
//                    }
//                    // 초기값으로 받고 초기화
//                    pin1.setTextColor(Color.parseColor("#CDCDCD"));
//                    pin2.setTextColor(Color.parseColor("#CDCDCD"));
//                    pin3.setTextColor(Color.parseColor("#CDCDCD"));
//                    pin4.setTextColor(Color.parseColor("#CDCDCD"));
//                    edtKeyboard.setText("");
//                    edtKeyboard.getText().toString();
//
//                    // 데이터 전달부
//
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }
}