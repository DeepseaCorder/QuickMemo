package com.puresoftware.quickmemo.drawer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puresoftware.quickmemo.R;
import com.puresoftware.quickmemo.room.UserFolder;

import java.util.ArrayList;
import java.util.List;

public class UserFolderAdapter extends BaseAdapter {

    String TAG = UserFolderAdapter.class.getSimpleName();

    List<com.puresoftware.quickmemo.room.UserFolder> folderList = new ArrayList<>();
    Context context;

    @Override
    public int getCount() {
        return folderList.size();
    }

    @Override
    public Object getItem(int i) {
        return folderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (context == null) {
            context = viewGroup.getContext();
        }

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.main_drawer_select_set_item, viewGroup, false);
        }

        LinearLayout linDrawerUserFolder = view.findViewById(R.id.lin_main_drawer_user_folder);
        TextView tvDrawerUserTitle = view.findViewById(R.id.tv_main_drawer_user_title);
        TextView tvDrawerUserCount = view.findViewById(R.id.tv_main_drawer_user_count);

        UserFolder folder = folderList.get(i);

        tvDrawerUserTitle.setText(folder.getTitle());
        tvDrawerUserCount.setText(folder.getCount() + "");

        linDrawerUserFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "드로우어블 개인 폴더 터치됨");
            }
        });

        linDrawerUserFolder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.i(TAG, "드로우어블 개인 폴더 롱 터치됨");


                return true;
            }
        });

        return view;
    }

    public void addItem(com.puresoftware.quickmemo.room.UserFolder folder) {
        folderList.add(folder);
    }
}
