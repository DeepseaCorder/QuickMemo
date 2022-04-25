package com.puresoftware.quickmemo;

import static com.puresoftware.quickmemo.R.drawable.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
    boolean boldSwitch = false;
    boolean cancelLineSwitch = false;
    boolean underLineSwitch = false;

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

                if (starSwitch == false) {
                    btnStar.setImageResource(ic_write_activity_star_regular);
                } else {
                    btnStar.setImageResource(ic_write_activity_star_selected);
                }
            }
        });

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lockSwitch = !lockSwitch;
                Log.i(TAG, "starSwitch status: " + lockSwitch + "");

                if (lockSwitch == false) {
                    btnLock.setImageResource(ic_write_activity_lock_solid);
                } else {
                    btnLock.setImageResource(R.drawable.ic_write_activity_lock_selected);
                }
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
                popupWindow.showAsDropDown(btnFontSize, 0, -310);

//                popupWindow.showAsDropDown(btnFontSize, 0, btnFontSize.getBottom() - 60);

                TextView tvFontSmall = inflateView.findViewById(R.id.tv_write_activity_menu_align_ledt);
                TextView tvFontMid = inflateView.findViewById(R.id.tv_write_activity_menu_align_center);
                TextView tvFontBig = inflateView.findViewById(R.id.tv_write_activity_menu_align_right);

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

                popupWindow.showAsDropDown(btnColor, 0, -310);

                ImageView btnMenuRed = inflateView.findViewById(R.id.tv_write_activity_menu_color_red);
                ImageView btnMenuOrange = inflateView.findViewById(R.id.tv_write_activity_menu_color_orange);
                ImageView btnMenuYellow = inflateView.findViewById(R.id.tv_write_activity_menu_color_yellow);
                ImageView btnMenuGreen = inflateView.findViewById(R.id.tv_write_activity_menu_color_green);
                ImageView btnMenuSky = inflateView.findViewById(R.id.tv_write_activity_menu_color_sky);
                ImageView btnMenuBlue = inflateView.findViewById(R.id.tv_write_activity_menu_color_blue);
                ImageView btnMenuPurple = inflateView.findViewById(R.id.tv_write_activity_menu_color_purple);
                ImageView btnMenuBlack = inflateView.findViewById(R.id.tv_write_activity_menu_color_black);

                btnMenuRed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setTextColor(Color.parseColor("#FF4D4D"));
                        popupWindow.dismiss();

                    }
                });

                btnMenuOrange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setTextColor(Color.parseColor("#FFA94D"));
                        popupWindow.dismiss();

                    }
                });

                btnMenuYellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setTextColor(Color.parseColor("#FFED4D"));
                        popupWindow.dismiss();

                    }
                });

                btnMenuGreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setTextColor(Color.parseColor("#8BFF4D"));
                        popupWindow.dismiss();

                    }
                });

                btnMenuSky.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setTextColor(Color.parseColor("#4DC1FF"));
                        popupWindow.dismiss();

                    }
                });

                btnMenuBlue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setTextColor(Color.parseColor("#4D65FF"));
                        popupWindow.dismiss();

                    }
                });

                btnMenuPurple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setTextColor(Color.parseColor("#A04DFF"));
                        popupWindow.dismiss();

                    }
                });

                btnMenuBlack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setTextColor(Color.parseColor("#000000"));
                        popupWindow.dismiss();

                    }
                });

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

                popupWindow.showAsDropDown(btnAlignment, 0, -310);

                TextView tvLeft = inflateView.findViewById(R.id.tv_write_activity_menu_align_ledt);
                TextView tvCenter = inflateView.findViewById(R.id.tv_write_activity_menu_align_center);
                TextView tvRight = inflateView.findViewById(R.id.tv_write_activity_menu_align_right);

                tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setAlignLeft();
                    }
                });

                tvCenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setAlignCenter();
                    }
                });

                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        richEditor.setAlignRight();
                    }
                });
            }
        });

        btnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (boldSwitch == false) {
                    boldSwitch = true;
                    btnBold.setImageResource(ic_bold_selected);
                    richEditor.setBold();
                } else {
                    boldSwitch = false;
                    btnBold.setImageResource(ic_bold_solid);
                    richEditor.setBold();
                }
            }
        });

        btnCancelLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelLineSwitch == false) {
                    cancelLineSwitch = true;
                    btnCancelLine.setImageResource(ic_strikethrough_selected);
                    richEditor.setStrikeThrough();
                } else {
                    cancelLineSwitch = false;
                    btnCancelLine.setImageResource(ic_strikethrough_solid);
                    richEditor.setStrikeThrough();

                }
            }
        });

        btnUnderLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (underLineSwitch == false) {
                    underLineSwitch = true;
                    btnUnderLine.setImageResource(ic_underline_selected);
                    richEditor.setUnderline();
                } else {
                    underLineSwitch = false;
                    btnUnderLine.setImageResource(ic_underline_solid);
                    richEditor.setUnderline();
                }
            }
        });
    }
}