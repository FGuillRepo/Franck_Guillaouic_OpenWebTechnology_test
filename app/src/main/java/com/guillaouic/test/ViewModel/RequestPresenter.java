package com.guillaouic.test.ViewModel;

import android.content.Context;

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
    public void RequestBooks_NetworkData(Context context, String title) {
        View.ShowRequestProgress();
        Interactor.getData_Network(context, this,title);
          //  View.noNetworkConnectivity();
    }

    @Override
    public void RequestBooks_Database(Context context) {
        View.ShowRequestProgress();
        Interactor.GetData_Database(context,this);
        //  View.noNetworkConnectivity();
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