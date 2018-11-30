package com.guillaouic.test.Network;

import android.content.Context;

import com.guillaouic.test.activity.Utils.Utils;

/**
 *
 *  Class managing Presenter and Interactor for Instagram data request
 */

public class RequestPresenter implements Presenter, Interactor.OnRequestFinishedListener {


    private RequestView View;
    private Interactor Interactor;

    public RequestPresenter(RequestView loginView) {
        this.View = loginView;
        this.Interactor = new InteractorImpl();
    }


    // Request Github repo data user

    @Override
    public void ReqestData(Context context, int page) {
        View.ShowRequestProgress();

        if (Utils.isConnected(context)) {
            Interactor.getData(context, this,page);
        }else{
            View.noNetworkConnectivity();
        }
    }


    @Override public void onDestroy() {
        View = null;
    }


    @Override
    public void onError() {
        if (View != null) {
            View.onError();
        }
    }

    @Override public void onSuccess() {

        if (View != null) {
            View.RequestSuccess();
        }
    }

    public Interactor getInteractor() {
        return Interactor;
    }

}