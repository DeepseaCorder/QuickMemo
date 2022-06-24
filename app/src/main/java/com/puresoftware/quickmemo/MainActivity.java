package com.puresoftware.quickmemo;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.puresoftware.quickmemo.room.AppDatabase;
import com.puresoftware.quickmemo.room.Memo;
import com.puresoftware.quickmemo.room.MemoDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Collectors;

import jp.wasabeef.richeditor.RichEditor;

public class MainActivity extends Activity {

    LinearLayout linTopcard1;
    LinearLayout linTopcard2;

    DrawerLayout menuNavi;
    View drawerView;
    ImageView btnMenu;
    FloatingActionButton fbtnWrite;
    ImageView btnSearch;

    TextView tvDrawerTitle;
    TextView tvDrawerEmail;
    ImageView btnDrawerSettings;

    String TAG = MainActivity.class.getSimpleName();

    View firstView;
    View secondView;

    List<Memo> memos;
    Memo lastMemo;
    Memo secondMemo;
    TextView tvMainCardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenu = findViewById(R.id.btn_main_menu);
        menuNavi = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawerView = (View) findViewById(R.id.v_main_drawer);
        fbtnWrite = findViewById(R.id.fbtn_main_write);
        btnSearch = findViewById(R.id.btn_main_search);
        tvMainCardCount = findViewById(R.id.tv_main_card_count);


        // NavigationView navigationView = (NavigationView) findViewById(R.id.main_navi_view);

        // DrawerLayout 내 오브젝트
        tvDrawerTitle = drawerView.findViewById(R.id.tv_main_drawer_custom_ID);
        tvDrawerEmail = drawerView.findViewById(R.id.tv_main_drawer_custom_Email);
        btnDrawerSettings = drawerView.findViewById(R.id.btn_main_drawer_custom_settings);

        // RoomDB 메모내용 불러오기
        AppDatabase db = AppDatabase.getInstance(this);
        MemoDao memoDao = db.dao();


        new Thread(new Runnable() {
            @Override
            public void run() {
                memos = memoDao.getAll();
            }
        }).start();

        // Top 카드 메뉴
        if (linTopcard1 == null || linTopcard2 == null) {
            linTopcard1 = findViewById(R.id.lin_main_infate_top_card1);
            linTopcard2 = findViewById(R.id.lin_main_infate_top_card2);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            firstView = inflater.inflate(R.layout.main_top_card_item, linTopcard1, true);
            secondView = inflater.inflate(R.layout.main_top_card_item_important, linTopcard2, true);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 메모 불러오기

                // data
                long recentStamp = 0;
                long imporRecentStamp = 0;
                //향상된 for문

                if (memos.size() <= 0) {

                    return;
                }
                lastMemo = memos.get(memos.size() - 1);
                recentStamp = lastMemo.timestamp;

                // firstView
                TextView tvTopCardTitle = firstView.findViewById(R.id.tv_main_last_card_title);
                TextView tvTopCardDate = firstView.findViewById(R.id.tv_main_last_card_date);
                RichEditor tvTopCardContent = firstView.findViewById(R.id.tv_main_last_card_content);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd a H:mm", Locale.KOREA);

                tvTopCardTitle.setText(lastMemo.title);
                tvTopCardDate.setText(sdf.format(recentStamp));
                tvTopCardContent.setHtml(lastMemo.content);
                tvTopCardContent.setInputEnabled(false);

                // secondView
                TextView tvImportantCardTitle = secondView.findViewById(R.id.tv_main_impo_card_title);
                TextView tvImportantCardDate = secondView.findViewById(R.id.tv_main_impo_card_date);
                TextView tvImportantCardLock = secondView.findViewById(R.id.tv_main_top_card_impor_lock);
                RichEditor tvImportantCardContent = secondView.findViewById(R.id.tv_main_impo_card_content);
                tvImportantCardContent.setInputEnabled(false);

                List<Memo> starList = new ArrayList<>();
//
//                // 초보자용 코드
//                for(int i = 0; i<memos.size(); i++) {
//                    Memo memo = memos.get(i);
//
//                    if (memo.star == true) {
//                        starList.add(memo);
//                    }
//                }

                // 리스트에서 필터링 할 때 사용
                // 실무 (Java8, 람다, steam, filter)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    starList = memos.stream().filter(memo -> memo.star == true).collect(Collectors.toList());
                }

                if (starList.size() > 0) {

                    secondMemo = starList.get(starList.size() - 1);
                    tvImportantCardTitle.setText(secondMemo.title);
                    tvImportantCardDate.setText(sdf.format(secondMemo.timestamp));
                    tvImportantCardContent.setHtml(secondMemo.content);
                    linTopcard2.setVisibility(View.GONE);

                    if (secondMemo.lock == true) {
                        tvImportantCardLock.setVisibility(View.VISIBLE);
                        tvImportantCardLock.setText("잠금");
                    }

                } else {
                    linTopcard2.setVisibility(View.GONE);
                }
            }

        }).start();

        // 카드 메뉴
        RecyclerView recyclerView = findViewById(R.id.rec_main_card);
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        Adapter adapter = new Adapter();

        if (memos.size() > 0) {
            tvMainCardCount.setText(memos.size() + "개의 메모");
            Log.i(TAG, "memosize:" + memos.size());

//            adapter.setArrayData();

//            adapter.setArrayData();

        }else{

        }


        recyclerView.setAdapter(adapter);

        // 최근 카드 (last)
        linTopcard1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                editActivityIntent(lastMemo.title, lastMemo.content, lastMemo.timestamp, lastMemo.star, lastMemo.lock);
            }
        });

        // 중요 카드 (second)
        linTopcard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editActivityIntent(secondMemo.title, secondMemo.content, secondMemo.timestamp, secondMemo.star, secondMemo.lock);
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuNavi.openDrawer(drawerView);

                // 설정 버튼
                btnDrawerSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, AppSettingsActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });

        fbtnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {

        if (menuNavi.isDrawerOpen(drawerView) == true) {
            menuNavi.closeDrawer(drawerView);
        } else {
            super.onBackPressed(); // 종료 기능을 수행
        }
    }

    public void editActivityIntent(String title, String content, long timestamp, boolean star, boolean lock) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("timestamp", timestamp);
        intent.putExtra("lock", star);
        intent.putExtra("star", lock);
        startActivity(intent);
    }


}