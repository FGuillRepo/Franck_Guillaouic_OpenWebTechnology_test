package com.guillaouic.test.database;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.guillaouic.test.pojo.Item;

import java.util.List;

/*
 *  Room DaoAccess : functions to insert, fetch, and delete books.
 * */

@Dao
 public interface DaoAccess {

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 void insertBook (Item book);

 @Query ("SELECT * FROM item")
 LiveData<List<Item>> fetchListBooks();

 @Query("DELETE FROM item")
 public void clearBooktable();
 }
