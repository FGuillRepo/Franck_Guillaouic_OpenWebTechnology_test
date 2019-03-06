package com.guillaouic.test.database;
import android.arch.lifecycle.LiveData;
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
 
 @Insert
 void insertBook (Book movies);
 @Query ("SELECT * FROM book_model")
List<Book> fetchListBooks();
 @Update
 void updateMovie (Book movies);
 @Delete
 void deleteMovie (Book movies);
 }