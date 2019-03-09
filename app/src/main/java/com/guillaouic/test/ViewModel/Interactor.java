package com.guillaouic.test.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableField;

import com.guillaouic.test.model.bookModel.Book;
import com.guillaouic.test.model.bookModel.Item;

import java.util.List;

public interface Interactor {

    interface OnRequestFinishedListener {
        void onError();
        void onSuccess();
    }
    MutableLiveData<Book> getData();
    LiveData<Book> getBooks_Network(String title);
    void getData_Network(Context context, OnRequestFinishedListener listener, String title);
    void GetData_Database(String title);

}