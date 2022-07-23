package com.puresoftware.quickmemo.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Memo.class, UserFolder.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MemoDao dao();

    private static AppDatabase INSTANCE = null;

    public static AppDatabase getInstance(Context context) {

        // Room을 사용하기 위한 객체 인스턴스
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "room.db").build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
