package com.guillaouic.test.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.guillaouic.test.AppExecutors;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;
import com.guillaouic.test.utils.DatabaseInitializer;

import java.util.ArrayList;
import java.util.List;

@android.arch.persistence.room.Database(entities = {Item.class}, version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract DaoAccess getItemDAO();
    private static Database sInstance;
    private static String TAG_database = "book";
    private static final String TAG = DatabaseInitializer.class.getName();
    private static final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    private static MediatorLiveData<List<Item>> data = new MediatorLiveData<>();

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


    public void getBookDatabase(Database database) {
        final LiveData<List<Item>> sections = database.getItemDAO().fetchListBooks();

        data.addSource(sections, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> sectionList) {
                data.setValue(sectionList);
                Log.d("fromDB","fromdb");
            }
        });
    }

    public MediatorLiveData<List<Item>> getData() {
        return data;
    }
}