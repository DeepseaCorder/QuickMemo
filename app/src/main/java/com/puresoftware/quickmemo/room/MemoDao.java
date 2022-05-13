package com.puresoftware.quickmemo.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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

    @Insert
    void insert(Memo memo);

    @Delete
    void delete(Memo memo);

}