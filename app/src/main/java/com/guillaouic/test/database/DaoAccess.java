package com.guillaouic.test.database;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;

import java.util.List;

@Dao
 public interface DaoAccess {


 @Update
 void insertBook (Book book);
 @Query ("SELECT * FROM book_model")
 LiveData<List<Book>> fetchListBooks();
 }