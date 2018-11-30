package com.guillaouic.test.Network;

import android.content.Context;

import io.reactivex.Observable;

public interface Interactor {

    interface OnRequestFinishedListener {
        void onError();
        void onSuccess();
    }

    void getData(Context context, OnRequestFinishedListener listener, int page);

}