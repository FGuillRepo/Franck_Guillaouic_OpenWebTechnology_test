package com.guillaouic.test.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.guillaouic.test.network.Interactor;
import com.guillaouic.test.network.InteractorImpl;
import com.guillaouic.test.database.Database;
import com.guillaouic.test.pojo.bookModel.Book;
import com.guillaouic.test.pojo.bookModel.Item;

import java.util.List;

/*
 *  DataRepository : Permit to call/observe data books from network or database,
 * */

public class DataRepository {


    private static DataRepository sInstance;

    private final Database mDatabase;

    protected Interactor interactor;

    private MutableLiveData<Book> data;
    private MutableLiveData<List<Item>> data_database;

    public DataRepository(Database database) {
        mDatabase = database;
        interactor = new InteractorImpl();
        if (data == null) {
            data = interactor.getData();
        }
        if (data_database == null) {
            data_database = database.getData();
        }
    }


    public Interactor loadNetworkData() {
        return interactor;
    }

    public void getBookDatabase() {
        mDatabase.getBookDatabase(mDatabase);
    }

    public MutableLiveData<Book> getBook() {
        return data;
    }

    public MutableLiveData<Book> getData() {
        return data;
    }

    public LiveData<List<Item>> getData_database() {
        return data_database;
    }

    public static DataRepository getInstance(final Database database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }
}
