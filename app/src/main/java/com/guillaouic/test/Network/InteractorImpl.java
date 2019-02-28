package com.guillaouic.test.Network;

import android.content.Context;
import android.util.Log;

import java.util.List;

import com.guillaouic.test.Model.Repository;
import com.guillaouic.test.RetroFit.RetroFitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.guillaouic.test.fragment.Repository_fragment.repositoryAdapter;

public class InteractorImpl implements Interactor {

    OnRequestFinishedListener listener;

    // Request repository and fill recycler adapter

    @Override
    public void getData(final Context context, final OnRequestFinishedListener listener, int page) {
        Log.d("page",String.valueOf(page));
        this.listener = listener;
        Call<List<Repository>> call = new RetroFitClient().getRetroFitService(context).getRepository(page);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                List<Repository>  repositoryList= response.body();
                try {
                    for (Repository repo : repositoryList){
                        repositoryAdapter.add(repo);
                    }

                }catch (NullPointerException e){
                }

                listener.onSuccess();
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                listener.onError();
            }

        });
    }
}

