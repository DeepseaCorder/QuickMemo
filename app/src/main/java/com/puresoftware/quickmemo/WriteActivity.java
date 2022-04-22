package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;

import jp.wasabeef.richeditor.RichEditor;

public class WriteActivity extends AppCompatActivity {

    //title line
    ImageView btnBack;
    EditText edtTitle;
    ImageView btnStar;
    ImageView btnLock;

    //textLine
    RichEditor richEditor;

    //menuLine
    TextView btnFontSize;
    ImageView btnColor;
    ImageView btnAlignment;
    ImageView btnBold;
    ImageView btnCancelLine;
    ImageView btnUnderLine;


    //switch
    boolean starSwitch = false;
    boolean lockSwitch = false;

    String TAG = WriteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        btnBack = findViewById(R.id.btn_write_activity_back);
        edtTitle = findViewById(R.id.edt_write_activity_title);
        btnStar = findViewById(R.id.btn_write_activity_star);
        btnLock = findViewById(R.id.btn_write_activity_lock);
        richEditor = findViewById(R.id.v_write_activity_richeditor);
        btnFontSize = (TextView) findViewById(R.id.btn_write_activity_fontsize);
        btnColor = findViewById(R.id.btn_write_activity_color);
        btnAlignment = findViewById(R.id.btn_write_activity_alignment);
        btnBold = findViewById(R.id.btn__write_activity_bold);
        btnCancelLine = findViewById(R.id.btn__write_activity_canceline);
        btnUnderLine = findViewById(R.id.btn__write_activity_underline);


        // 기본으로 사용할 폰트의 크기
        richEditor.setFontSize(5);

        // 입력창
        richEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {

                String title = edtTitle.getText().toString().trim();
                String content = richEditor.getHtml();
                boolean star = starSwitch;
                boolean lock = lockSwitch;

                long timeStamp = System.currentTimeMillis();

                Log.i(TAG, "write status "
                        + "title: " + title
                        + "/content: " + content
                        + "/star: " + star
                        + "" + "/lock: " + lock
                        + "" + "timestamp: " + timeStamp + "");

                JSONObject memo = new JSONObject();
                try {
                    memo.put("title", title);
                    memo.put("content", content);
                    memo.put("star", star);
                    memo.put("lock", lock);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                '
//                {
//                    "title" : "adasd",
//                    "content" : "<h1>asdasd</h1>"
//                }
//                '

                SharedPreferences preferences = getSharedPreferences("memos", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("" + timeStamp, memo.toString());
                editor.commit();

            }
        });


        // 저장 후 메뉴로
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                starSwitch = !starSwitch;

                Log.i(TAG, "starSwitch status: " + starSwitch + "");
            }
        });

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lockSwitch = !lockSwitch;
                Log.i(TAG, "starSwitch status: " + lockSwitch + "");

            }
        });


        btnFontSize.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {


                //팝업윈도우 구현
                PopupWindow popupWindow = new PopupWindow(view);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View inflateView = inflater.inflate(R.layout.write_layout_fontsize, null);
                popupWindow.setContentView(inflateView);
                popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //팝업윈도우 파라미터
                popupWindow.setAnimationStyle(R.style.Animation_AppCompat_Dialog); // 안드로이드 기본 애니메이션임. 이걸 넣어야 상향일때 애니메이션이 적용됨.
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true); //팝업 뷰 포커스도 주고
                popupWindow.update();
                popupWindow.setOutsideTouchable(true); //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

//                popupWindow.showAsDropDown(btnFontSize, 0, -50);
//                popupWindow.showAtLocation(btnFontSize, Gravity.LEFT, 0,
//                        btnFontSize.getBottom() - 60);
                popupWindow.showAsDropDown(btnFontSize, 0, -300);

//                popupWindow.showAsDropDown(btnFontSize, 0, btnFontSize.getBottom() - 60);


                TextView tvFontSmall = inflateView.findViewById(R.id.tv_write_activity_menu_font_small);
                TextView tvFontMid = inflateView.findViewById(R.id.tv_write_activity_menu_font_mid);
                TextView tvFontBig = inflateView.findViewById(R.id.tv_write_activity_menu_font_big);

                tvFontSmall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvFontSmall.setTextColor(Color.parseColor("#FFFF2B2B"));
                        tvFontMid.setTextColor(Color.parseColor("#000000"));
                        tvFontBig.setTextColor(Color.parseColor("#000000"));

                        richEditor.setFontSize(3);
                        popupWindow.dismiss();


                    }
                });

                tvFontMid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setFontSize(5);
                        popupWindow.dismiss();

                        tvFontSmall.setTextColor(Color.BLACK);
                        tvFontMid.setTextColor(Color.RED);
                        tvFontBig.setTextColor(Color.BLACK);

                    }
                });

                tvFontBig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setFontSize(10);
                        popupWindow.dismiss();

                        tvFontSmall.setTextColor(Color.BLACK);
                        tvFontMid.setTextColor(Color.BLACK);
                        tvFontBig.setTextColor(Color.RED);

                    }
                });

                //error: popupwindow를 키면 keyboard가 바뀌고 그 현상이 보임.

            }
        });

        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //팝업윈도우 구현
                PopupWindow popupWindow = new PopupWindow(view);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View inflateView = inflater.inflate(R.layout.write_layout_color, null);

                popupWindow.setContentView(inflateView);
                popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //팝업윈도우 파라미터
                popupWindow.setAnimationStyle(R.style.Animation_AppCompat_Dialog); // 안드로이드 기본 애니메이션임. 이걸 넣어야 상향일때 애니메이션이 적용됨.
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true); //팝업 뷰 포커스도 주고
                popupWindow.update();
                popupWindow.setOutsideTouchable(true); //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

//                popupWindow.showAsDropDown(btnFontSize, 0, -50);
                popupWindow.showAsDropDown(btnColor, 0, -300);

//                popupWindow.showAtLocation(btnColor, Gravity., 0, 0);
//                popupWindow.showAsDropDown(btnFontSize, 0, btnFontSize.getBottom() - 60);


            }
        });

        btnAlignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //팝업윈도우 구현
                PopupWindow popupWindow = new PopupWindow(view);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View inflateView = inflater.inflate(R.layout.write_layout_alignment, null);
                popupWindow.setContentView(inflateView);
                popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //팝업윈도우 파라미터
                popupWindow.setAnimationStyle(R.style.Animation_AppCompat_Dialog); // 안드로이드 기본 애니메이션임. 이걸 넣어야 상향일때 애니메이션이 적용됨.
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true); //팝업 뷰 포커스도 주고
                popupWindow.update();
                popupWindow.setOutsideTouchable(true); //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

//                popupWindow.showAsDropDown(btnFontSize, 0, -50);
//                popupWindow.showAtLocation(btnFontSize, Gravity.LEFT, 0,
//                        btnFontSize.getBottom() - 60);
                popupWindow.showAsDropDown(btnAlignment, 0, -300);

//                popupWindow.showAsDropDown(btnFontSize, 0, btnFontSize.getBottom() - 60);

            }
        });

        btnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        btnCancelLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnUnderLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}