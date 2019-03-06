package com.guillaouic.test.RetroFit;

import android.content.Context;

import com.guillaouic.test.activity.Utils.Login_URL;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {

    /*
    *   RetroFit class
    * */

    public  Retrofit createRetroFit(Context context) {
        Login_URL login_url=new Login_URL(context);
        return new Retrofit.Builder()
                 .baseUrl(login_url.getBASE_URL())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public  RetrofitService getRetroFitService(Context context) {
        final Retrofit retrofit = createRetroFit(context);
        return retrofit.create(com.guillaouic.test.RetroFit.RetrofitService.class);
    }

}