package com.guillaouic.test.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.Nullable;

import com.guillaouic.test.pojo.bookModel.Item;


import java.util.List;

/*
 *  Books Database : instance of book database.
 *  function to retieve books.
 * */

@android.arch.persistence.room.Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract DaoAccess getItemDAO();

    private static Database sInstance;

    private static String name_database = "book";

    private static MediatorLiveData<List<Item>> data = new MediatorLiveData<>();

    public static Database getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, Database.class, name_database)
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }


    /*
     *  Called (by repository) to retrieve books from database.
     * */

    public void getBookDatabase(Database database) {
        final LiveData<List<Item>> sections = database.getItemDAO().fetchListBooks();

        data.addSource(sections, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> sectionList) {
                data.setValue(sectionList);
            }
        });
    }

    public MediatorLiveData<List<Item>> getData() {
        return data;
    }
}