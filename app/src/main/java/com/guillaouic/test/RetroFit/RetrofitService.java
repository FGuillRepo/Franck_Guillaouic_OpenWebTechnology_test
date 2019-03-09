package com.guillaouic.test.RetroFit;


import android.databinding.ObservableField;

import com.guillaouic.test.model.bookModel.Book;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitService {

    @GET("books/v1/volumes")
    Observable<Book> getBooks(@Query("q") String title);
}