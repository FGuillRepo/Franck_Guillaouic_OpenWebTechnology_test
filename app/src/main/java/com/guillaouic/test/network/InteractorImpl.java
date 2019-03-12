package com.guillaouic.test.network;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.guillaouic.test.pojo.Item;
import com.guillaouic.test.retrofit.RetroFitClient;
import com.guillaouic.test.pojo.Book;
import com.guillaouic.test.utils.Message;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/*
 *  InteractorImple : contain function  to call Book API, and emit livedata value to repository,
 * */

public class InteractorImpl {

    static MediatorLiveData<Book> data = new MediatorLiveData<>();
    static MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MediatorLiveData<Book> getData() {
        return data;
    }

    public static MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Request Book API and emit value to repository if succeed
    public MutableLiveData<Book> getBooks_Network(String search) {

        Observable<Book> call = new RetroFitClient().getRetroFitService().getBooks(search);
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
                        errorMessage.postValue(Message.no_network.name());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }
}

