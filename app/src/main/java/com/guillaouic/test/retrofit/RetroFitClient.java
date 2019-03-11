package com.guillaouic.test.retrofit;

import android.content.Context;
import com.guillaouic.test.utils.Login_URL;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {

    /*
    *   RetroFit class
    * */


    public  Retrofit createRetroFit() {
        return new Retrofit.Builder()
                .baseUrl(Login_URL.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public  RetrofitService getRetroFitService() {
        final Retrofit retrofit = createRetroFit();
        return retrofit.create(com.guillaouic.test.retrofit.RetrofitService.class);
    }

}