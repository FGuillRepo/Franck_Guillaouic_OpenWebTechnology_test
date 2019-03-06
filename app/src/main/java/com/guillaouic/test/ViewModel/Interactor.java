package com.guillaouic.test.ViewModel;

import android.content.Context;

public interface Interactor {

    interface OnRequestFinishedListener {
        void onError();
        void onSuccess();
    }

    void getData_Network(Context context, OnRequestFinishedListener listener, String title);
    void GetData_Database(Context context, OnRequestFinishedListener listener);

}