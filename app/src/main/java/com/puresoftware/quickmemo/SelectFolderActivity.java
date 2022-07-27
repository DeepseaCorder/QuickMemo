package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.puresoftware.quickmemo.drawer.UserFolderAdapter;
import com.puresoftware.quickmemo.room.AppDatabase;
import com.puresoftware.quickmemo.room.Memo;
import com.puresoftware.quickmemo.room.MemoDao;
import com.puresoftware.quickmemo.room.UserFolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectFolderActivity extends AppCompatActivity {

    LinearLayout linMainFolder;
    TextView tvMainCount;
    LinearLayout linImpoFolder;
    TextView tvImpoCount;
    ListView lvUserFolder;

    UserFolderAdapter adapter;
    List<UserFolder> folderList;
    UserFolder folder;
    List<Memo> memoList;
    List<Memo> noneFolderList;
    List<Memo> starList;
    ArrayList setList;

    String TAG = SelectFolderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_folder);

        linMainFolder = findViewById(R.id.lin_selectfolder_main_folder);
        tvMainCount = findViewById(R.id.tv_selectfolder_main_count);
        linImpoFolder = findViewById(R.id.lin_selectfolder_impo_folder);
        tvImpoCount = findViewById(R.id.tv_selectfolder_impo_count);
        lvUserFolder = findViewById(R.id.lv_selectfolder_user_folder);

        // RoomDB 메모내용 불러오기
        AppDatabase db = AppDatabase.getInstance(this);
        MemoDao memoDao = db.dao();

        folderList = new ArrayList<>();
        memoList = new ArrayList<>();

        // 폴더와 휴지통 없는 메모들 가져오기
        new Thread(new Runnable() {
            @Override
            public void run() {

                // 데이터 받기
                folderList = memoDao.getFolderAll();
                memoList = memoDao.getNotTrashAll(false);

                // 중요리스트,폴더없는 리스트 카운트
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    starList = new ArrayList<>();
                    starList = memoList.stream().filter(memo -> memo.star == true).collect(Collectors.toList());
                    noneFolderList = new ArrayList<>();
                    noneFolderList = memoList.stream().filter(memo -> memo.folder == null && memo.star == false).collect(Collectors.toList());
                }

                // view에 뿌리기
                tvImpoCount.setText(starList.size() + "");
                tvMainCount.setText(noneFolderList.size() + "");

                // adapter에 아이템 넣기
                adapter = new UserFolderAdapter();
                for (UserFolder folder : folderList) {
                    adapter.addItem(folder);
                }
                lvUserFolder.setAdapter(adapter);

                Log.i(TAG, "foldersize:" + folderList.size() + ",memosize:" + memoList.size());
            }
        }).start();

        // 인텐트된 포지션 데이터 가져오기(오류가 너무 많아 Bundle로 하기)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        setList = bundle.getStringArrayList("set");

        for (int i = 0; i < setList.size(); i++) {
            Log.i(TAG, "list:" + setList.get(i) + "");
        }

        // 폴더명 없애기.
        linMainFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFolder(memoList, setList, folderList, memoDao, -2);

                //// 백업코드
//                for (int i = 0; i < setList.size(); i++) {
//                    Memo updateMemo = memoList.get(Integer.parseInt((String) setList.get(i)));
//                    updateMemo.folder = null; // 조건문 추가
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            memoDao.updateData(updateMemo.title,
//                                    updateMemo.content,
//                                    updateMemo.star,
//                                    updateMemo.lock,
//                                    updateMemo.timestamp,
//                                    updateMemo.folder);
//                        }
//                    }).start();
//
//                    Log.i(TAG, "folderUpdate " +
//                            "title:" + updateMemo.title +
//                            ",folder:" + updateMemo.folder +
//                            ",posi:" + setList.get(i) + "");
//                    finish();
//                }
            }
        });

        linImpoFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFolder(memoList, setList, folderList, memoDao, -1);
            }
        });

        lvUserFolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                folder = folderList.get(i);

                setFolder(memoList, setList, folderList, memoDao, i);
            }
        });
    }

    public void setFolder(List<Memo> memoList, ArrayList setList, List<UserFolder> folderList, MemoDao memoDao, int count) {

        for (int i = 0; i < setList.size(); i++) {
            Memo updateMemo = memoList.get(Integer.parseInt((String) setList.get(i)));

            // 입력조건에 따른 폴더명이 변경됨
            switch (count) {

                // 메인 코드
                case -2:
                    updateMemo.folder = null; // 조건문 추가
                    updateMemo.setStar(false);
                    break;

                // 중요 코드
                case -1:
                    updateMemo.folder = null;
                    updateMemo.setStar(true);
                    break;
            }

            // 유저 코드
            if (count > -1) {
                String name = folderList.get(count).title;
                updateMemo.setFolder(name);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    memoDao.updateData(updateMemo.title,
                            updateMemo.content,
                            updateMemo.star,
                            updateMemo.lock,
                            updateMemo.timestamp,
                            updateMemo.folder);
                }
            }).start();

            Log.i(TAG, "folderUpdate " +
                    "title:" + updateMemo.title +
                    ",folder:" + updateMemo.folder +
                    ",posi:" + setList.get(i) + "");
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}