package com.guillaouic.test.retrofit;


import com.guillaouic.test.pojo.bookModel.Book;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RetrofitService {

    @GET("books/v1/volumes")
    Observable<Book> getBooks(@Query("q") String title);
}