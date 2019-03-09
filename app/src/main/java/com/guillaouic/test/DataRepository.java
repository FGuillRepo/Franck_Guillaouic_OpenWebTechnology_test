package com.guillaouic.test;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.guillaouic.test.ViewModel.Interactor;
import com.guillaouic.test.ViewModel.InteractorImpl;
import com.guillaouic.test.database.Database;
import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;

import java.util.List;

public class DataRepository {


    private static DataRepository sInstance;

    private final Database mDatabase;

    protected Interactor interactor;

    private MutableLiveData<Book> data;

    public DataRepository(Database database) {
        mDatabase = database;
        interactor = new InteractorImpl();
        if (data == null) {
            data = interactor.getData();
        }
    }


    public Interactor loadNetworkData() {
        return interactor;
    }


    public LiveData<Book> getBook() {
        return data;
    }

    public LiveData<List<Book>> loadBook() {
        return mDatabase.getItemDAO().fetchListBooks();
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
