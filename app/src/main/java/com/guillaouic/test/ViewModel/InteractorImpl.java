package com.guillaouic.test.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.guillaouic.test.RetroFit.RetroFitClient;
import com.guillaouic.test.database.Database;
import com.guillaouic.test.model.bookModel.Book;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InteractorImpl implements Interactor {

    OnRequestFinishedListener listener;
    static MediatorLiveData<Book> data = new MediatorLiveData<>();
    private Context context;




    public MediatorLiveData<Book> getData() {
        return data;
    }

    // Request repository and fill recycler adapter
    public MutableLiveData<Book> getBooks_Network(String title) {

        Observable<Book> call = new RetroFitClient().getRetroFitServic().getBooks(title);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Book>() {

                    @Override
                    public void onNext(Book book) {
                        try {
                                data.postValue(book);

                        } catch (NullPointerException e) {
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }

    @Override
    public void getData_Network(final Context context, final OnRequestFinishedListener listener, String title) {
        this.listener = listener;
    }



}

