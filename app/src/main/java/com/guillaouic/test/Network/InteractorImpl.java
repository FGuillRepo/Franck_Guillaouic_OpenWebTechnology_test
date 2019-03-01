package com.guillaouic.test.Network;

import android.content.Context;
import android.util.Log;

import java.util.List;

import com.guillaouic.test.Model.Repository;
import com.guillaouic.test.RetroFit.RetroFitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.guillaouic.test.fragment.Repository_fragment.repositoryAdapter;

public class InteractorImpl implements Interactor {

    OnRequestFinishedListener listener;

    // Request repository and fill recycler adapter

    @Override
    public void getData(final Context context, final OnRequestFinishedListener listener, int page) {
        this.listener = listener;
        Observable<List<Repository>> call = new RetroFitClient().getRetroFitService(context).getRepository(page);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Repository>>() {

                    @Override
                    public void onNext(List<Repository> response) {

                        try {
                            for (Repository repo : response) {
                                Log.d("page", String.valueOf(page));
                                Log.d("page", repo.getFullName());
                                repositoryAdapter.add(repo);
                            }

                        } catch (NullPointerException e) {
                            listener.onError();
                        }

                        listener.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}

