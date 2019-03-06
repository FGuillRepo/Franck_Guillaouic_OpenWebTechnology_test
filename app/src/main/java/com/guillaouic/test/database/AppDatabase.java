package com.guillaouic.test.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.guillaouic.test.model.bookModel.Book;

@Database(entities = {Book.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoAccess getItemDAO();
}