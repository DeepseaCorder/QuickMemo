package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

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


            }
        });

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true); //팝업 뷰 포커스도 주고
                popupWindow.update();
                popupWindow.setOutsideTouchable(true); //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAtLocation(btnFontSize, Gravity.BOTTOM, btnFontSize.getLeft() - 100,
                        btnFontSize.getBottom() - 60);

                TextView tvFontSmall = inflateView.findViewById(R.id.tv_write_activity_menu_font_small);
                TextView tvFontMid = inflateView.findViewById(R.id.tv_write_activity_menu_font_mid);
                TextView tvFontBig = inflateView.findViewById(R.id.tv_write_activity_menu_font_big);

                tvFontSmall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setFontSize(3);
                        popupWindow.dismiss();
                    }
                });

                tvFontMid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setFontSize(5);
                        popupWindow.dismiss();

                    }
                });

                tvFontBig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setFontSize(10);
                        popupWindow.dismiss();

                    }
                });

                //error: popupwindow를 키면 keyboard가 바뀌고 그 현상이 보임.

            }
        });

        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnAlignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                richEditor.setFontSize(30);


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