package com.guillaouic.test.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.guillaouic.test.pojo.bookModel.Book;

public interface Interactor {

    MediatorLiveData<Book> getData();

    LiveData<Book> getBooks_Network(String title);
}