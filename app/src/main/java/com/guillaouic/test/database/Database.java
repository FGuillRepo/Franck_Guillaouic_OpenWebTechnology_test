package com.guillaouic.test.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.guillaouic.test.AppExecutors;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;

import java.util.ArrayList;
import java.util.List;

@android.arch.persistence.room.Database(entities = {Book.class}, version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract DaoAccess getItemDAO();
    private static Database sInstance;
    private static String TAG_database = "book";


    public static Database getInstance(final Context context) {
        if (sInstance == null) {
            sInstance =Room.databaseBuilder(context, Database.class, "book")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return sInstance;
    }


}