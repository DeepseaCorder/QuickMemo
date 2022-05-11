package com.puresoftware.quickmemo.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Memo {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "star")
    public boolean star;

    @ColumnInfo(name = "lock")
    public boolean lock;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    @Override
    public String toString() {
        return "Memo{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", star=" + star +
                ", lock=" + lock +
                ", timestamp=" + timestamp +
                '}';
    }
}
