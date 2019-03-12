package com.guillaouic.test.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.guillaouic.test.network.InteractorImpl;
import com.guillaouic.test.database.Database;
import com.guillaouic.test.pojo.Book;
import com.guillaouic.test.pojo.Item;

import java.util.List;

/*
 *  DataRepository : Permit to call data books from network or database. Emit data to BookViewModel
 * */

public class DataRepository {


    private static DataRepository sInstance;

    private Database mDatabase;

    protected InteractorImpl network_interactor;

    private MutableLiveData<String> ErrorMessage;
    private MutableLiveData<List<Item>> data_database;
    private MutableLiveData<Book> data;


    public DataRepository(Database database) {
        mDatabase = database;
        network_interactor = new InteractorImpl();
        if (data == null) {
            ErrorMessage = network_interactor.getErrorMessage();
            data = network_interactor.getData();
        }
        if (data_database == null) {
            data_database = database.getData();
        }
    }

    // Constructor use for unit test
    public DataRepository() {
        data = new MutableLiveData<>();
        network_interactor = new InteractorImpl();
            data = network_interactor.getData();
    }

    public InteractorImpl loadNetworkData() {
        return network_interactor;
    }

    public void getBookDatabase() {
        mDatabase.getBookDatabase(mDatabase);
    }

    public MutableLiveData<Book> getBook() {
        return data;
    }


    public LiveData<List<Item>> getBook_database() {
        return data_database;
    }

    public MutableLiveData<String> getErrorMessage() {
        return ErrorMessage;
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
