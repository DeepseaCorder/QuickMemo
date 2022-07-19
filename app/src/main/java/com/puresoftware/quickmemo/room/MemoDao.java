package com.puresoftware.quickmemo.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MemoDao {

//    @Query("SELECT * FROM user")
//    List<User> getAll();
//
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<User> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);
//
//    @Insert
//    void insert(User user);
//
//    @Insert
//    void insertAll(User... users);
//
//    @Delete
//    void delete(User user);
//
//    @Query("DELETE FROM user where first_name LIKE :firstName")
//    void deleteByName(String firstName);

    @Query("SELECT * FROM Memo")
    List<Memo> getAll();

    @Query("SELECT * FROM Memo where trash=:isTrash")
    List<Memo> getNotTrashAll(boolean isTrash);

    @Insert
    void insert(Memo memo);

    @Delete
    void delete(Memo memo);

//    // update 테이블명 set 컬럼1=값1, 컬럼2=값2
//    @Query("UPDATE user set first_name=:first where uid=:uid")
//    void updateFirstName(int uid, String first);

    @Query("UPDATE Memo set title=:title,content=:content,star=:star,lock=:lock where timestamp=:timeStamp")
    void updateData(String title, String content, boolean star, boolean lock, long timeStamp);

    @Query("UPDATE Memo set trash=:isTransh where uid=:uid")
    void updateTrash(boolean isTransh, int uid);
}
