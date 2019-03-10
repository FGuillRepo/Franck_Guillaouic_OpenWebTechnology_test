package com.guillaouic.test.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.guillaouic.test.database.Database;
import com.guillaouic.test.model.bookModel.Book;

public interface Interactor {

    interface OnRequestFinishedListener {
        void onError();
        void onSuccess();
    }
    MediatorLiveData<Book> getData();
    LiveData<Book> getBooks_Network(String title);
    void getData_Network(Context context, OnRequestFinishedListener listener, String title);
}