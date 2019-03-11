package com.guillaouic.test;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.guillaouic.test.database.Database;
import com.guillaouic.test.repository.DataRepository;


public class Application extends android.app.Application {


    public static Application application;
    public static Context context;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
        setContext(context);
        application=this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }


    public Database getDatabase() {
        return Database.getInstance(this);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }

    public static void setContext(Context context) {
        Application.context = context;
    }

    public static synchronized Application getApplication() {
        return application;
    }

}

