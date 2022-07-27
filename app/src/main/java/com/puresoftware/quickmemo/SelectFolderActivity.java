package com.puresoftware.quickmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

                // memoList 카운트
                tvMainCount.setText(memoList.size() + "");

                // starList 카운트
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    starList = new ArrayList<>();
                    starList = memoList.stream().filter(memo -> memo.star == true).collect(Collectors.toList());
                }
                tvImpoCount.setText(starList.size() + "");

                // adapter에 아이템 넣기
                adapter = new UserFolderAdapter();
                for (UserFolder folder : folderList) {
                    adapter.addItem(folder);
                }
                lvUserFolder.setAdapter(adapter);

                Log.i(TAG, "foldersize:" + folderList.size() + ",memosize:" + memoList.size());
            }
        }).start();

        // 인텐트된 포지션 데이터 가져오기
        Intent intent = getIntent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}