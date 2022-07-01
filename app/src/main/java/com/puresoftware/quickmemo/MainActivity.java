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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.puresoftware.quickmemo.room.AppDatabase;
import com.puresoftware.quickmemo.room.Memo;
import com.puresoftware.quickmemo.room.MemoDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    ImageView vEmpty;

    TextView tvDrawerTitle;
    TextView tvDrawerEmail;
    ImageView btnDrawerSettings;

    String TAG = MainActivity.class.getSimpleName();

    View firstView;
    View secondView;

    List<Memo> memos;
    Memo lastMemo;
    Memo secondMemo;
    Memo memo;
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
        vEmpty = findViewById(R.id.v_main_empty);


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
                    linTopcard1.setVisibility(View.GONE);
                    linTopcard2.setVisibility(View.GONE);

                    return;
                }
                lastMemo = memos.get(memos.size() - 1);
                Log.i("lock", "lock" + lastMemo.lock);
                recentStamp = lastMemo.timestamp;

                // firstView
                TextView tvTopCardTitle = firstView.findViewById(R.id.tv_main_last_card_title);
                TextView tvTopCardDate = firstView.findViewById(R.id.tv_main_last_card_date);
                RichEditor tvTopCardContent = firstView.findViewById(R.id.tv_main_last_card_content);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd a H:mm", Locale.KOREA);

                if (lastMemo.lock == true) {
                    tvTopCardTitle.setText(lastMemo.title);
                    tvTopCardContent.setHtml("");
                    tvTopCardDate.setText(sdf.format(lastMemo.timestamp));

                } else {
                    tvTopCardTitle.setText(lastMemo.title);
                    tvTopCardContent.setHtml(lastMemo.content);
                    tvTopCardDate.setText(sdf.format(lastMemo.timestamp));
                }

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

                if (starList.size() > 0) { // 데이터가 1개 이상이면 메모 전시, 없으면 메모 삭제.

                    secondMemo = starList.get(starList.size() - 1);

                    if (secondMemo.lock == true) {
                        tvImportantCardLock.setVisibility(View.VISIBLE);
                        tvImportantCardLock.setText("잠금");
                        tvImportantCardTitle.setText(secondMemo.title);
                        tvImportantCardDate.setText(sdf.format(secondMemo.timestamp));

                    } else {
                        tvImportantCardTitle.setText(secondMemo.title);
                        tvImportantCardDate.setText(sdf.format(secondMemo.timestamp));
                        tvImportantCardContent.setHtml(secondMemo.content);
                    }
                    linTopcard2.setVisibility(View.VISIBLE); // 메모 전시

                } else {
                    linTopcard2.setVisibility(View.GONE); // 메모 비전시
                }
            }


        }).start();

        // 카드 메뉴
        RecyclerView recyclerView = findViewById(R.id.rec_main_card);
        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        Adapter adapter = new Adapter();

        if (memos.size() > 0) {
            tvMainCardCount.setText(memos.size() + "개의 메모");
            Log.i(TAG, "memosize:" + memos.size());
            vEmpty.setVisibility(View.GONE); // 비어 있음 이미지 끄기

            // 같으면 왼쪽에 데이터를, 다르면 오른쪽에 데이터를 넣는 방식으로 한다.
            int left = 0;
            int right = 0;

            for (int i = 0; i < memos.size(); i++) {

                Log.i(TAG, "memonum:" + memos.get(0));
                memo = new Memo();
                memo.title = memos.get(i).title;
                memo.content = memos.get(i).content;
                memo.timestamp = memos.get(i).timestamp;
                memo.lock = memos.get(i).lock;
                memo.star = memos.get(i).star;
                adapter.setArrayData(memo);
            }
        } else {
            vEmpty.setVisibility(View.VISIBLE); // 비어있음 이미지 켜기
        }


        recyclerView.setAdapter(adapter);

        // 최근 카드 (last)
        linTopcard1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                lockContentTop(lastMemo);
            }
        });

        // 중요 카드 (second)
        linTopcard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockContentTop(secondMemo);
            }
        });

        // 메인 카드 관련 코드는 Adapter에 있음.

        // 검색
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        // 메뉴
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
                        finish();
                    }
                });
            }
        });

        // 글쓰기 플로팅 버튼
        fbtnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    // onCreate

    // 그 외 기능
    // 백 버튼을 누를 때, 드로우메뉴가 있으면 드로우메뉴 취소, 없으면 앱 끝내기.
    @Override
    public void onBackPressed() {

        if (menuNavi.isDrawerOpen(drawerView) == true) {
            menuNavi.closeDrawer(drawerView);
        } else {
            super.onBackPressed(); // 종료 기능을 수행
        }
    }

    // 상단 카드들의(중요,최근 카드) Lock 여부를 확인하여 그에 맞는 장소로 이동하기.
    public void lockContentTop(Memo memo) {

        // 인텐트 데이터를 모아놓기
        Intent intent = new Intent();
        intent.putExtra("title", memo.title);
        intent.putExtra("content", memo.content);
        intent.putExtra("timestamp", memo.timestamp);
        intent.putExtra("lock", memo.star);
        intent.putExtra("star", memo.lock);

        // 락 모드가 true면 PIN을 입력하는 곳으로 가기.
        if (memo.lock == true) {
            intent = new Intent(MainActivity.this, PINActivity.class);
            startActivity(intent);
            finish();

            // 락 모드가 false면 바로 수정창으로 가기.
        } else {
            intent = new Intent(MainActivity.this, EditActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

// 모든 인텐트에 startActivity > finish를 해야 하는 이유
// 1. Adapter의 롱 터치시 아이템 삭제를 위한 기능 때문인데, 뷰 홀더를 갱신해주지 않으면 초기변수가 그대로 존재하고 있어서 초기화가 필함.